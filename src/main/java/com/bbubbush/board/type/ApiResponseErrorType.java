package com.bbubbush.board.type;

public enum ApiResponseErrorType {

  SUCCESS(200, "")
  , INVALID_PARAMETER(501, "%s 을(를) 확인해주세요.")
  , TOO_LONG_TEXT(502, "%s 은(는) %s글자를 넘을 수 없습니다.")
  , DUPLICATE_LIST_VALUE(503, "%s 은(는) 중복된 값을 입력할 수 없습니다.")
  , TOO_MANY_COLLECTION(504, "%s 은(는) %s개 까지 입력할 수 있습니다.")
  , ARTICLE_NOT_FOUND(511, "해당 게시글을 찾을 수 없습니다.")
  , NOT_MODIFIED_DATA(522, "변경된 데이터가 없습니다.")
  , NOT_MATCHED_EXCEL_HEADER(530, "입력하신 엑셀파일의 헤더 정보가 일치하지 않습니다.")
  , SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다. 관리자에게 문의하세요.");

  private final Integer code;
  private final String message;

  ApiResponseErrorType(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return getMessage("");
  }

  public String getMessage(String... target) {
    return message.formatted(target);
  }

}
