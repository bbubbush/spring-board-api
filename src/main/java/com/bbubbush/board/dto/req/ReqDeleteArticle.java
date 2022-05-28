package com.bbubbush.board.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqDeleteArticle {

  @Positive(message = "INVALID_PARAMETER")
  private long id;

}
