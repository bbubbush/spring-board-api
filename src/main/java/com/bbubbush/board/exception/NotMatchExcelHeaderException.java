package com.bbubbush.board.exception;

import com.bbubbush.board.type.ApiResponseErrorType;

public class NotMatchExcelHeaderException extends CommonApiException {

  public NotMatchExcelHeaderException() {
    super(ApiResponseErrorType.NOT_MATCHED_EXCEL_HEADER);
  }

  public NotMatchExcelHeaderException(String addMessage) {
    super(ApiResponseErrorType.NOT_MATCHED_EXCEL_HEADER.getMessage() + " " + addMessage);
  }

}
