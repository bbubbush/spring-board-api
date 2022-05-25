package com.bbubbush.board.mapper;

import com.bbubbush.board.annotation.DynamicMapper;
import com.bbubbush.board.vo.board.BoardTagVO;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import java.util.List;

@Mapper
public interface BoardTagMapper extends DynamicMapper<BoardTagVO> {

  @SelectProvider(type = SqlProviderAdapter.class, method = "select")
  List<String> findArticleTags(SelectStatementProvider selectProvider);

  @InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
  int insertArticleTags(MultiRowInsertStatementProvider insertProvider);

  @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
  int deleteArticleTags(DeleteStatementProvider deleteProvider);

}
