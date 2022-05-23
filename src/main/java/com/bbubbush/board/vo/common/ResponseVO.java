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
    return of(ApiResponseType.SUCCESS, data);
  }

  protected static <T> ResponseVO createFailVO() {
    return createFailVO(ApiResponseType.SERVER_ERROR);
  }

  protected static <T> ResponseVO createFailVO(ApiResponseType responseType) {
    if (ApiResponseType.SUCCESS.equals(responseType)) {
      return of(ApiResponseType.SERVER_ERROR);
    }
    return of(responseType);
  }

  public static <T> ResponseVO of(ApiResponseType responseType) {
    return of(responseType, null);
  }

  public static <T> ResponseVO of(ApiResponseType responseType, T data) {
    return ResponseVO.builder()
      .errorCode(responseType.getCode())
      .errorMsg(responseType.getMessage())
      .data(data)
      .build();
  }

}
