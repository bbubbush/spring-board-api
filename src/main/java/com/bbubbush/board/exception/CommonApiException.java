package com.bbubbush.board.exception;

import com.bbubbush.board.type.ApiResponseErrorType;

public class CommonApiException extends RuntimeException{

  public CommonApiException() {
    super(ApiResponseErrorType.SERVER_ERROR.getMessage());
  }

  public CommonApiException(String message) {
    super(message);
  }

  public CommonApiException(ApiResponseErrorType responseType) {
    super(responseType.getMessage());
  }

}
