package com.bbubbush.board.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@SuperBuilder
public class ReqInsertArticle {

  private String subject;
  private String text;
  private String writer;
  @Builder.Default
  private List<String> tags = new ArrayList<>();

  private Long targetArticleId;

//  @Builder
//  private ReqInsertArticle(String subject, String text, String writer, List<String> tags, Long targetArticleId) {
//    this.subject = subject;
//    this.text = text;
//    this.writer = writer;
//    this.tags = tags;
//    this.targetArticleId = targetArticleId;
//  }
}
