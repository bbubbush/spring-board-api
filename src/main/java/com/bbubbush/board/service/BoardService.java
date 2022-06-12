package com.bbubbush.board.service;

import com.bbubbush.board.dto.common.ReqSendMail;
import com.bbubbush.board.dto.req.ReqExcelUploadArticle;
import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqUpdateArticle;
import com.bbubbush.board.dto.res.ResExcelUploadArticle;
import com.bbubbush.board.dto.res.ResSearchArticle;
import com.bbubbush.board.exception.ArticleNotFoundException;
import com.bbubbush.board.exception.NotModifiedDataException;
import com.bbubbush.board.mapper.BoardMapper;
import com.bbubbush.board.mapper.BoardTagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.bbubbush.board.entity.NoticeBoardSqlSupport.*;
import static com.bbubbush.board.entity.NoticeBoardTagSqlSupport.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardService {

  private final BoardMapper boardMapper;
  private final BoardTagMapper boardTagMapper;
  private final MailService mailService;
  private final ExcelService excelService;

  @Transactional(readOnly = true)
  public ResSearchArticle findArticle(Long articleId) {
    final ResSearchArticle findArticle = boardMapper.findArticle(findArticleProvider(articleId));
    if (findArticle == null) {
      throw new ArticleNotFoundException();
    }

    return findArticleTagById(findArticle);
  }

  @Transactional(readOnly = true)
  public List<ResSearchArticle> findArticles() {
    return boardMapper.findArticles(findArticlesProvider())
      .stream()
      .map(this::findArticleTagById)
      .toList();
  }

  public int insertArticle(ReqInsertArticle reqInsertArticle) {
    final int insertRows = boardMapper.insertArticle(insertArticleProvider(reqInsertArticle));
    if (insertRows == 0) {
      throw new NotModifiedDataException();
    }

    boardTagMapper.insertArticleTags(insertArticleTagsProvider(reqInsertArticle));
    return insertRows;
  }

  public int updateArticle(ReqUpdateArticle reqUpdateArticle) {
    reqUpdateArticle.setUpdateArticleId();
    deleteArticleTags(reqUpdateArticle.getId());

    final int updateRows = boardMapper.updateArticle(updateArticleProvider(reqUpdateArticle));
    if (updateRows == 0) {
      throw new NotModifiedDataException();
    }

    boardTagMapper.insertArticleTags(insertArticleTagsProvider(reqUpdateArticle));
    return updateRows;
  }

  public int deleteArticle(Long articleId) {
    final ResSearchArticle findArticle = findArticle(articleId);
    deleteArticleTags(articleId);

    final int deleteRows = boardMapper.deleteArticle(deleteArticleProvider(articleId));
    if (deleteRows == 0) {
      throw new NotModifiedDataException();
    }

    final ReqSendMail reqSendMail = ReqSendMail.createDeleteArticleDto(findArticle.getSubject());
    mailService.sendMail(reqSendMail);
    return deleteRows;
  }

  public ResExcelUploadArticle insertArticleInExcel(ReqExcelUploadArticle reqExcelUploadArticle) {
    final MultipartFile uploadFile = reqExcelUploadArticle.getUploadFile();
    final List<ReqInsertArticle> reqInsertArticles = excelService.transformToDto(uploadFile, ReqInsertArticle.class);
    reqInsertArticles.forEach(this::insertArticle);
    return ResExcelUploadArticle.builder()
      .totalInsertRows(reqInsertArticles.size())
      .successInsertRows(reqInsertArticles.size())
      .build();
  }

  private ResSearchArticle findArticleTagById(ResSearchArticle findArticle) {
    final List<String> articleTags = boardTagMapper.findArticleTags(findArticleTagsProvider(findArticle.getArticleId()));
    findArticle.setTag(articleTags);
    return findArticle;
  }

  private void deleteArticleTags(Long articleId) {
    boardTagMapper.deleteArticleTags(deleteArticleTagsProvider(articleId));
  }

}
