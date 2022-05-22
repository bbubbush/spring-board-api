package com.bbubbush.board.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqSendMail {

  private String to;
  private String from;
  private String subject;
  private String text;

  public static ReqSendMail createDeleteArticleDto(Long articleId) {
    return ReqSendMail
      .builder()
      .to("bbubbush@gmail.com")
      .from("upskilling@bbubbush.com")
      .subject("[Notification] 게시물 삭제")
      .text(articleId + " 번 게시글이 삭제되었습니다")
      .build();
  }

}
