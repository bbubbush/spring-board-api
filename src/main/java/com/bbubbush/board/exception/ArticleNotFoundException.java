package com.bbubbush.board.exception;

import com.bbubbush.board.type.ApiResponseErrorType;

public class ArticleNotFoundException extends CommonApiException {

  public ArticleNotFoundException() {
    super(ApiResponseErrorType.ARTICLE_NOT_FOUND);
  }

  public ArticleNotFoundException(String addMessage) {
    super(ApiResponseErrorType.ARTICLE_NOT_FOUND.getMessage() + " " + addMessage);
  }

}
