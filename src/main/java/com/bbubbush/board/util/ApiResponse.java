package com.bbubbush.board.util;

import com.bbubbush.board.vo.common.ResponseVO;

public class ApiResponse<T> extends ResponseVO<T> {

  public static <T> ResponseVO success(T data) {
    return ResponseVO.createSuccessVO(data);
  }

}
