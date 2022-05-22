package com.bbubbush.board.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqSendMail {

  private String to;
  private String from;
  private String subject;
  private String text;

  public static ReqSendMail createDeleteArticleDto(String subject) {
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    return ReqSendMail
      .builder()
      .to("bbubbush@gmail.com")
      .from("upskilling@bbubbush.com")
      .subject("[게시물 삭제] %s 이(가) 삭제되었습니다.".formatted(subject))
      .text("%s 에 해당 게시글이 삭제되었습니다.".formatted(
        LocalDateTime
          .now()
          .format(dateTimeFormatter)))
      .build();
  }

}
