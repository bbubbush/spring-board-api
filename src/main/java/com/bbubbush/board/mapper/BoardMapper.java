package com.bbubbush.board.mapper;

import com.bbubbush.board.annotation.DynamicMapper;
import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.res.ResSearchArticle;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import java.util.List;

@Mapper
public interface BoardMapper extends DynamicMapper<ReqInsertArticle> {

  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @Result(column = "creator", property = "writer", jdbcType = JdbcType.VARCHAR)
  ResSearchArticle findArticle(SelectStatementProvider selectProvider);

  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  @Result(column = "creator", property = "writer", jdbcType = JdbcType.VARCHAR)
  List<ResSearchArticle> findArticles(SelectStatementProvider selectProvider);

  @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
  @Options(useGeneratedKeys = true, keyProperty = "row.targetArticleId")
  int insertArticle(InsertStatementProvider<ReqInsertArticle> insertProvider);

  @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
  int updateArticle(UpdateStatementProvider updateStatementProvider);

  @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
  int deleteArticle(DeleteStatementProvider deleteProvider);

}
