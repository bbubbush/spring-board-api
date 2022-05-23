package com.bbubbush.board.config;

import com.bbubbush.board.exception.ArticleNotFoundException;
import com.bbubbush.board.exception.CommonApiException;
import com.bbubbush.board.exception.NotModifiedDataException;
import com.bbubbush.board.type.ApiResponseType;
import com.bbubbush.board.util.ApiResponse;
import com.bbubbush.board.vo.common.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ArticleNotFoundException.class)
  public ResponseVO articleNotFoundExceptionHandler(ArticleNotFoundException e) {
    return responseAndLoggingError(ApiResponseType.ARTICLE_NOT_FOUND, e);
  }

  @ExceptionHandler(NotModifiedDataException.class)
  public ResponseVO notModifiedDataExceptionHandler(NotModifiedDataException e) {
    return responseAndLoggingError(ApiResponseType.NOT_MODIFIED_DATA, e);
  }

  @ExceptionHandler(CommonApiException.class)
  public ResponseVO commonApiExceptionHandler(CommonApiException e) {
    return responseAndLoggingError(ApiResponseType.SERVER_ERROR, e);
  }

  @ExceptionHandler(Exception.class)
  public ResponseVO exceptionHandler(Exception e) {
    return responseAndLoggingError(ApiResponseType.SERVER_ERROR, e);
  }

  private ResponseVO responseAndLoggingError(ApiResponseType articleNotFound, Exception e) {
    log.error("Exception type : {}, Message : {}", e.getClass().getSimpleName(), e.getMessage());
    return ApiResponse.error(articleNotFound.getCode(), e.getMessage());
  }

}
