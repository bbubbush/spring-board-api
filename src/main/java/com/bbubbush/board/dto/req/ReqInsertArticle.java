package com.bbubbush.board.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqInsertArticle {

  private String subject;
  private String text;
  private String writer;
  private List<String> tags = new ArrayList<>();

  private Long targetArticleId;
}
