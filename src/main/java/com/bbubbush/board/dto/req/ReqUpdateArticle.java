package com.bbubbush.board.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ReqUpdateArticle extends ReqInsertArticle{

  private long id;

  public void setUpdateArticleId() {
    this.setTargetArticleId(id);
  }

}
