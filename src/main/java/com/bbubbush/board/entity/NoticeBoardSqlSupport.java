package com.bbubbush.board.entity;

import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqUpdateArticle;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

public class NoticeBoardSqlSupport  {

  public static final NoticeBoard noticeBoard = new NoticeBoard();
  public static final SqlColumn<Long> articleId = noticeBoard.articleId;
  public static final SqlColumn<String> subject = noticeBoard.subject;
  public static final SqlColumn<String> text = noticeBoard.text;
  public static final SqlColumn<String> creator = noticeBoard.creator;
  public static final SqlColumn<Date> createdDate = noticeBoard.createdDate;
  public static final SqlColumn<String> modifier = noticeBoard.modifier;
  public static final SqlColumn<Date> lastModifiedDate = noticeBoard.lastModifiedDate;

  public static final class NoticeBoard extends AliasableSqlTable<NoticeBoard> {
    public final SqlColumn<Long> articleId = column("article_id", JDBCType.INTEGER);
    public final SqlColumn<String> subject = column("subject", JDBCType.VARCHAR);
    public final SqlColumn<String> text = column("text", JDBCType.VARCHAR);
    public final SqlColumn<String> creator = column("creator", JDBCType.VARCHAR);
    public final SqlColumn<Date> createdDate = column("created_date", JDBCType.DATE);
    public final SqlColumn<String> modifier = column("modifier", JDBCType.VARCHAR);
    public final SqlColumn<Date> lastModifiedDate = column("last_modified_date", JDBCType.DATE);

    public NoticeBoard() {
      super("notice_board", NoticeBoard::new);
    }
  }

  public static SelectStatementProvider findArticleProvider(long id) {
    return select(articleId, subject, text, creator, createdDate, lastModifiedDate)
      .from(noticeBoard)
      .where(articleId, isEqualTo(id))
      .build()
      .render(RenderingStrategies.MYBATIS3);
  }

  public static SelectStatementProvider findArticlesProvider() {
    return select(articleId, subject, text, creator, createdDate, lastModifiedDate)
      .from(noticeBoard)
      .build()
      .render(RenderingStrategies.MYBATIS3);
  }

  public static InsertStatementProvider<ReqInsertArticle> insertArticleProvider(ReqInsertArticle reqInsertArticle) {
    return insert(reqInsertArticle)
      .into(noticeBoard)
      .map(subject).toProperty("subject")
      .map(text).toProperty("text")
      .map(creator).toProperty("writer")
      .map(modifier).toProperty("writer")
      .build()
      .render(RenderingStrategies.MYBATIS3);
  }

  public static UpdateStatementProvider updateArticleProvider(ReqUpdateArticle reqUpdateArticle) {
    return update(noticeBoard)
      .set(lastModifiedDate).equalTo(Timestamp.valueOf(LocalDateTime.now()))
      .set(subject).equalTo(reqUpdateArticle.getSubject())
      .set(text).equalTo(reqUpdateArticle.getText())
      .where(articleId, isEqualTo(reqUpdateArticle.getId()))
      .build()
      .render(RenderingStrategies.MYBATIS3);
  }

  public static DeleteStatementProvider deleteArticleProvider(long id) {
    return deleteFrom(noticeBoard)
      .where(articleId, isEqualTo(id))
      .build()
      .render(RenderingStrategies.MYBATIS3);
  }

}
