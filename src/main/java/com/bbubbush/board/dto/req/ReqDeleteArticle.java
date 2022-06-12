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
public class ReqDeleteArticle {

  @Positive(message = "INVALID_PARAMETER")
  @Schema(description = "게시물 번호", example = "11")
  private Long id;

}
