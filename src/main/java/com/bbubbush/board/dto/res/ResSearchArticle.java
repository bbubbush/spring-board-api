package com.bbubbush.board.dto.res;

import com.bbubbush.board.util.MaskingUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResSearchArticle {

  private Long articleId;
  private String subject;
  private String text;
  private String writer;
  private List<String> tag = new ArrayList<>();
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;

  public void setWriter(String writer) {
    this.writer = MaskingUtil.maskUserName(writer);
  }
}
