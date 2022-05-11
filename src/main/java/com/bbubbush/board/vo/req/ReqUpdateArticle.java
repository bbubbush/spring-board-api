package com.bbubbush.board.vo.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqUpdateArticle {

  private Long id;
  private String subject;
  private String text;
  private List<String> tags = new ArrayList<>();

}
