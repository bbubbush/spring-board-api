package com.bbubbush.board.service;

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

  @Transactional(readOnly = true)
  public ResSearchArticle findArticle(Long articleId) {
    final ResSearchArticle findArticle = boardMapper.findArticle(articleId);
    final List<String> articleTags = boardMapper.findArticleTags(findArticle.getArticleId());
    findArticle.setTag(articleTags);
    return findArticle;
  }

  @Transactional(readOnly = true)
  public List<ResSearchArticle> findArticles() {
    final List<ResSearchArticle> articles = boardMapper.findArticles();
    articles.forEach(article -> article.setTag(boardMapper.findArticleTags(article.getArticleId())));
    return articles;
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
    deleteArticleTags(articleId);
    return boardMapper.deleteArticle(articleId);
  }

  private int deleteArticleTags(Long articleId) {
    return boardMapper.deleteArticleTags(articleId);
  }

}
