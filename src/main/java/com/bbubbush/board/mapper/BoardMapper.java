package com.bbubbush.board.mapper;

import com.bbubbush.board.dto.ArticleDto;
import com.bbubbush.board.dto.ArticleTagDto;
import com.bbubbush.board.vo.req.ReqInsertArticle;
import com.bbubbush.board.vo.req.ReqUpdateArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

  ArticleDto findArticle(Long articleId);

  List<ArticleDto> findArticles();

  int insertArticle(ReqInsertArticle reqInsertArticle);

  int updateArticle(ReqUpdateArticle reqUpdateArticle);

  int deleteArticle(Long articleId);

  List<String> findArticleTags(Long articleId);

}
