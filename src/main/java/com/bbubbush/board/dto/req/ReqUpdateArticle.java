package com.bbubbush.board.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqUpdateArticle extends ReqInsertArticle{

  private long id;

  public void setUpdateArticleId() {
    this.setTargetArticleId(id);
  }

}
