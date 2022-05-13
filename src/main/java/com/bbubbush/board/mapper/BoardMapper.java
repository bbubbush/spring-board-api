package com.bbubbush.board.mapper;

import com.bbubbush.board.dto.res.ResSearchArticle;
import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqUpdateArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

  ResSearchArticle findArticle(Long articleId);

  List<ResSearchArticle> findArticles();

  int insertArticle(ReqInsertArticle reqInsertArticle);

  int updateArticle(ReqUpdateArticle reqUpdateArticle);

  int deleteArticle(Long articleId);

  List<String> findArticleTags(Long articleId);

  int insertArticleTags(ReqInsertArticle reqInsertArticle);

  int deleteArticleTags(Long articleId);
}
