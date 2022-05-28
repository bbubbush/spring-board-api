package com.bbubbush.board.entity;

import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.vo.board.BoardTagVO;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.sql.JDBCType;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

public class NoticeBoardTagSqlSupport {

  private static final NoticeBoardTag noticeBoardTag = new NoticeBoardTag();
  private static final SqlColumn<Long> articleId = noticeBoardTag.articleId;
  private static final SqlColumn<String> name = noticeBoardTag.name;
  private static final SqlColumn<String> creator = noticeBoardTag.creator;
  private static final SqlColumn<String> modifier = noticeBoardTag.modifier;

  public static final class NoticeBoardTag extends AliasableSqlTable<NoticeBoardTag> {
    public final SqlColumn<Long> articleId = column("article_id", JDBCType.INTEGER);
    public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);
    public final SqlColumn<String> creator = column("creator", JDBCType.VARCHAR);
    public final SqlColumn<String> modifier = column("modifier", JDBCType.VARCHAR);

    public NoticeBoardTag() {
      super("notice_board_tag", NoticeBoardTag::new);
    }
  }

  public static SelectStatementProvider findArticleTagsProvider(long inArticleId) {
    return select(name)
      .from(noticeBoardTag)
      .where(articleId, isEqualTo(inArticleId))
      .build()
      .render(RenderingStrategies.MYBATIS3);
  }

  public static MultiRowInsertStatementProvider<BoardTagVO> insertArticleTagsProvider(ReqInsertArticle reqInsertArticle) {
    return insertMultiple(reqInsertArticle.convertBoardTagVO())
      .into(noticeBoardTag)
      .map(articleId).toProperty("articleId")
      .map(name).toProperty("name")
      .map(creator).toProperty("writer")
      .map(modifier).toProperty("writer")
      .build()
      .render(RenderingStrategies.MYBATIS3);
  }

  public static DeleteStatementProvider deleteArticleTagsProvider(long inArticleId) {
    return deleteFrom(noticeBoardTag)
      .where(articleId, isEqualTo(inArticleId))
      .build()
      .render(RenderingStrategies.MYBATIS3);
  }

}
