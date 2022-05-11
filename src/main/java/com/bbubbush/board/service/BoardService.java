package com.bbubbush.board.service;

import com.bbubbush.board.dto.ArticleDto;
import com.bbubbush.board.mapper.BoardMapper;
import com.bbubbush.board.vo.req.ReqInsertArticle;
import com.bbubbush.board.vo.req.ReqUpdateArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardMapper boardMapper;

  public ArticleDto findArticle(Long articleId) {
    final ArticleDto findArticle = boardMapper.findArticle(articleId);
    final List<String> articleTags = boardMapper.findArticleTags(findArticle.getArticleId());
    findArticle.setTag(articleTags);
    return findArticle;
  }

  public List<ArticleDto> findArticles() {
    final List<ArticleDto> articles = boardMapper.findArticles();
    articles.forEach(article -> {
        final List<String> tags = boardMapper.findArticleTags(article.getArticleId());
        article.setTag(tags);
      });
    return articles;
  }

  public int insertArticle(ReqInsertArticle reqInsertArticle) {
    // TODO 개발중
    return boardMapper.insertArticle(reqInsertArticle);
  }

  public int updateArticle(ReqUpdateArticle reqUpdateArticle) {
    // TODO 개발중
    return boardMapper.updateArticle(reqUpdateArticle);
  }

  public int deleteArticle(Long articleId) {
    // TODO 개발중
    return boardMapper.deleteArticle(articleId);
  }

}
