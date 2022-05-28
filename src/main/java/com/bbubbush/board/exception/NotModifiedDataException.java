package com.bbubbush.board.exception;

import com.bbubbush.board.type.ApiResponseErrorType;

public class NotModifiedDataException extends CommonApiException {

  public NotModifiedDataException() {
    super(ApiResponseErrorType.NOT_MODIFIED_DATA);
  }

  public NotModifiedDataException(String addMessage) {
    super(ApiResponseErrorType.NOT_MODIFIED_DATA.getMessage() + " " + addMessage);
  }

}
