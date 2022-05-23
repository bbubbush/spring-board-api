package com.bbubbush.board.exception;

import com.bbubbush.board.type.ApiResponseType;

public class CommonApiException extends RuntimeException{

  public CommonApiException() {
    super(ApiResponseType.SERVER_ERROR.getMessage());
  }

  public CommonApiException(String message) {
    super(message);
  }

  public CommonApiException(ApiResponseType responseType) {
    super(responseType.getMessage());
  }

}
