package com.bbubbush.board.config;

import com.bbubbush.board.exception.ArticleNotFoundException;
import com.bbubbush.board.exception.CommonApiException;
import com.bbubbush.board.exception.NotModifiedDataException;
import com.bbubbush.board.type.ApiResponseType;
import com.bbubbush.board.util.ApiResponse;
import com.bbubbush.board.vo.common.ResponseVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ArticleNotFoundException.class)
  public ResponseVO articleNotFoundExceptionHandler(ArticleNotFoundException e) {
    return ApiResponse.error(ApiResponseType.ARTICLE_NOT_FOUND.getCode(), e.getMessage());
  }

  @ExceptionHandler(NotModifiedDataException.class)
  public ResponseVO notModifiedDataExceptionHandler(NotModifiedDataException e) {
    return ApiResponse.error(ApiResponseType.NOT_MODIFIED_DATA.getCode(), e.getMessage());
  }

  @ExceptionHandler(CommonApiException.class)
  public ResponseVO commonApiExceptionHandler(CommonApiException e) {
    return ApiResponse.error(ApiResponseType.SERVER_ERROR.getCode(), e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseVO exceptionHandler() {
    return ApiResponse.error(ApiResponseType.SERVER_ERROR);
  }

}
