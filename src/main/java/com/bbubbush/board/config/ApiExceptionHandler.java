package com.bbubbush.board.config;

import com.bbubbush.board.exception.ArticleNotFoundException;
import com.bbubbush.board.exception.CommonApiException;
import com.bbubbush.board.exception.NotModifiedDataException;
import com.bbubbush.board.type.ApiResponseErrorType;
import com.bbubbush.board.util.ApiResponse;
import com.bbubbush.board.vo.common.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ArticleNotFoundException.class)
  public ResponseVO articleNotFoundExceptionHandler(ArticleNotFoundException e) {
    return responseAndLoggingError(ApiResponseErrorType.ARTICLE_NOT_FOUND, e);
  }

  @ExceptionHandler(NotModifiedDataException.class)
  public ResponseVO notModifiedDataExceptionHandler(NotModifiedDataException e) {
    return responseAndLoggingError(ApiResponseErrorType.NOT_MODIFIED_DATA, e);
  }

  @ExceptionHandler(CommonApiException.class)
  public ResponseVO commonApiExceptionHandler(CommonApiException e) {
    return responseAndLoggingError(ApiResponseErrorType.SERVER_ERROR, e);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseVO methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
    final FieldError fieldError = e.getBindingResult()
      .getAllErrors()
      .stream()
      .findFirst()
      .map(error -> (FieldError)error)
      .orElse(new FieldError("Field", "", "SERVER_ERROR"));

    final String fieldName = fieldError.getField();
    final String errorName = fieldError.getDefaultMessage();
    final ApiResponseErrorType errorType = ApiResponseErrorType.valueOf(errorName);
    final String maxValue = getMaxValueInSizeAnno(errorType, fieldError.getArguments());
    return responseAndLoggingError(errorType, errorType.getMessage(fieldName, maxValue), e.getClass().getSimpleName());
  }

  @ExceptionHandler(Exception.class)
  public ResponseVO exceptionHandler(Exception e) {
    return responseAndLoggingError(ApiResponseErrorType.SERVER_ERROR, e);
  }

  private ResponseVO responseAndLoggingError(ApiResponseErrorType responseType, Exception e) {
    return responseAndLoggingError(responseType, e.getMessage(), e.getClass().getSimpleName());
  }

  private ResponseVO responseAndLoggingError(ApiResponseErrorType responseType, String message, String simpleExceptionName) {
    log.error("Exception type : {}, Message : {}", simpleExceptionName, message);
    return ApiResponse.error(responseType.getCode(), message);
  }

  private String getMaxValueInSizeAnno(ApiResponseErrorType errorType, Object[] args) {
    if (!(ApiResponseErrorType.TOO_LONG_TEXT.equals(errorType) || ApiResponseErrorType.TOO_MANY_COLLECTION.equals(errorType))) {
      return "";
    }
    return args[1].toString();
  }

}
