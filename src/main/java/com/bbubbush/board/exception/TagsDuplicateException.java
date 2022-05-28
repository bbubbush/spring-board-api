package com.bbubbush.board.exception;

import com.bbubbush.board.type.ApiResponseErrorType;

public class TagsDuplicateException extends CommonApiException {

  public TagsDuplicateException() {
    super(ApiResponseErrorType.DUPLICATE_LIST_VALUE);
  }

  public TagsDuplicateException(String addMessage) {
    super(ApiResponseErrorType.DUPLICATE_LIST_VALUE.getMessage() + " " + addMessage);
  }

}
