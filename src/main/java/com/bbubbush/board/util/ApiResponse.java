package com.bbubbush.board.util;

import com.bbubbush.board.type.ApiResponseType;
import com.bbubbush.board.vo.common.ResponseVO;

public class ApiResponse<T> extends ResponseVO<T> {

  public static <T> ResponseVO success(T data) {
    return ResponseVO.createSuccessVO(data);
  }

  public static ResponseVO error(ApiResponseType responseType) {
    return ResponseVO.createErrorVO(responseType);
  }

  public static ResponseVO error(int errorCode, String errorMessage) {
    return ResponseVO.of(errorCode, errorMessage);
  }

}
