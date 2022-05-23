package com.bbubbush.board.vo.common;

import com.bbubbush.board.type.ApiResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO<T> {

  private Integer errorCode;
  private String errorMsg;
  private T data;

  protected static <T> ResponseVO createSuccessVO(T data) {
    return of(ApiResponseType.SUCCESS.getCode(), ApiResponseType.SUCCESS.getMessage(), data);
  }

  protected static ResponseVO createErrorVO(ApiResponseType responseType) {
    if (ApiResponseType.SUCCESS.equals(responseType)) {
      return of(ApiResponseType.SERVER_ERROR);
    }
    return of(responseType);
  }

  protected static ResponseVO of(ApiResponseType responseType) {
    return of(responseType.getCode(), responseType.getMessage(), null);
  }

  protected static ResponseVO of(int code, String message) {
    return of(code, message, null);
  }

  protected static <T> ResponseVO of(int code, String message, T data) {
    return ResponseVO.builder()
      .errorCode(code)
      .errorMsg(message)
      .data(data)
      .build();
  }

}
