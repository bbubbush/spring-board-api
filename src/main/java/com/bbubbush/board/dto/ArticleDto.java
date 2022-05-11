package com.bbubbush.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

  private Long articleId;
  private String subject;
  private String text;
  private String writer;
  private List<String> tag = new ArrayList<>();
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

}
