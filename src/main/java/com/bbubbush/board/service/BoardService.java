package com.bbubbush.board.service;

import com.bbubbush.board.dto.common.ReqSendMail;
import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqUpdateArticle;
import com.bbubbush.board.dto.res.ResSearchArticle;
import com.bbubbush.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

  private final BoardMapper boardMapper;
  private final MailService mailService;

  @Transactional(readOnly = true)
  public ResSearchArticle findArticle(Long articleId) {
    final ResSearchArticle findArticle = boardMapper.findArticle(articleId);
    return findArticleTagById(findArticle);
  }

  @Transactional(readOnly = true)
  public List<ResSearchArticle> findArticles() {
    return boardMapper.findArticles()
      .stream()
      .map(this::findArticleTagById)
      .toList();
  }

  public int insertArticle(ReqInsertArticle reqInsertArticle) {
    final int insertRows = boardMapper.insertArticle(reqInsertArticle);
    boardMapper.insertArticleTags(reqInsertArticle);
    return insertRows;
  }

  public int updateArticle(ReqUpdateArticle reqUpdateArticle) {
    reqUpdateArticle.setUpdateArticleId();
    deleteArticleTags(reqUpdateArticle.getId());
    final int updateRows = boardMapper.updateArticle(reqUpdateArticle);
    boardMapper.insertArticleTags(reqUpdateArticle);
    return updateRows;
  }

  public int deleteArticle(Long articleId) {
    final ResSearchArticle findArticle = findArticle(articleId);

    deleteArticleTags(articleId);
    final int deleteRows = boardMapper.deleteArticle(articleId);

    final ReqSendMail reqSendMail = ReqSendMail.createDeleteArticleDto(findArticle.getSubject());
    mailService.sendMail(reqSendMail);
    return deleteRows;
  }

  private ResSearchArticle findArticleTagById(ResSearchArticle findArticle) {
    final List<String> articleTags = boardMapper.findArticleTags(findArticle.getArticleId());
    findArticle.setTag(articleTags);
    return findArticle;
  }

  private void deleteArticleTags(Long articleId) {
    boardMapper.deleteArticleTags(articleId);
  }

}
