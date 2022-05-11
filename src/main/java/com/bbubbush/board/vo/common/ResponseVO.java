package com.bbubbush.board.vo.common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseVO<T> {
  private Integer errorCode;
  private String errorMsg;
  private T data;
}
