package com.bbubbush.board.vo.common;

import com.bbubbush.board.type.ApiResponseErrorType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO<T> {

  @ApiModelProperty(value = "오류코드", example = "정상: 200, 오류: 그 외")
  private Integer errorCode;
  @ApiModelProperty(value = "오류메세지", example = "정상: \"\", 오류: 오류내용")
  private String errorMsg;
  @ApiModelProperty(value = "데이터")
  private T data;

  protected static <T> ResponseVO createSuccessVO(T data) {
    return of(ApiResponseErrorType.SUCCESS.getCode(), ApiResponseErrorType.SUCCESS.getMessage(), data);
  }

  protected static ResponseVO createErrorVO(ApiResponseErrorType responseType) {
    if (ApiResponseErrorType.SUCCESS.equals(responseType)) {
      return of(ApiResponseErrorType.SERVER_ERROR);
    }
    return of(responseType);
  }

  protected static ResponseVO of(ApiResponseErrorType responseType) {
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
