package com.bbubbush.board.exception;

import com.bbubbush.board.type.ApiResponseType;

public class NotModifiedDataException extends CommonApiException {

  public NotModifiedDataException() {
    super(ApiResponseType.NOT_MODIFIED_DATA);
  }

  public NotModifiedDataException(String addMessage) {
    super(ApiResponseType.NOT_MODIFIED_DATA.getMessage() + ", " + addMessage);
  }

}
