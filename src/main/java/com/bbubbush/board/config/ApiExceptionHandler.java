package com.bbubbush.board.config;

import com.bbubbush.board.exception.ArticleNotFoundException;
import com.bbubbush.board.exception.CommonApiException;
import com.bbubbush.board.exception.NotMatchExcelHeaderException;
import com.bbubbush.board.exception.NotModifiedDataException;
import com.bbubbush.board.type.ApiResponseErrorType;
import com.bbubbush.board.util.ApiResponse;
import com.bbubbush.board.vo.common.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(NotMatchExcelHeaderException.class)
  public ResponseVO notMatchExcelHeaderExceptionHandler(NotMatchExcelHeaderException e) {
    return responseAndLoggingError(ApiResponseErrorType.NOT_MATCHED_EXCEL_HEADER, e);
  }

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

  @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
  public ResponseVO bindExceptionHandler(BindException e) {
    final FieldError fieldError = e.getBindingResult()
      .getAllErrors()
      .stream()
      .findFirst()
      .map(error -> (FieldError)error)
      .orElse(new FieldError("Field", "", "SERVER_ERROR"));

    final String fieldName = fieldError.getField();
    final String errorName = fieldError.getDefaultMessage();
    final ApiResponseErrorType errorType = ApiResponseErrorType.valueOf(errorName);
    final String maxValue = getMaxValueInSizeAnnotation(errorType, fieldError.getArguments());
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

  private String getMaxValueInSizeAnnotation(ApiResponseErrorType errorType, Object[] args) {
    if (!(ApiResponseErrorType.TOO_LONG_TEXT.equals(errorType) || ApiResponseErrorType.TOO_MANY_COLLECTION.equals(errorType))) {
      return "";
    }
    if (args.length < 2) {
      return "";
    }
    return args[1].toString();
  }

}
