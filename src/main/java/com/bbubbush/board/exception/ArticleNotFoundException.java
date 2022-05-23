package com.bbubbush.board.exception;

import com.bbubbush.board.type.ApiResponseType;

public class ArticleNotFoundException extends CommonApiException {

  public ArticleNotFoundException() {
    super(ApiResponseType.ARTICLE_NOT_FOUND);
  }

  public ArticleNotFoundException(String addMessage) {
    super(ApiResponseType.ARTICLE_NOT_FOUND.getMessage() + ", " + addMessage);
  }

}
