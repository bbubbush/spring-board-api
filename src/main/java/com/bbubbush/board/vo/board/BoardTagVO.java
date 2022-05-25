package com.bbubbush.board.vo.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardTagVO {

  private Long articleId;
  private String name;
  private String writer;

}
