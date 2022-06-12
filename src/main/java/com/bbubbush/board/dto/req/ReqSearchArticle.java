package com.bbubbush.board.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ReqSearchArticle {

  @Positive(message = "INVALID_PARAMETER")
  private Long id;

}
