package com.bbubbush.board.type;

public enum ApiResponseType {

  SUCCESS(200, "")
  , ARTICLE_NOT_FOUND(501, "해당 게시글을 찾을 수 없습니다.")
  , NOT_MODIFIED_DATA(502, "변경된 데이터가 없습니다.")
  , SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다. 관리자에게 문의하세요.");

  private Integer code;
  private String message;

  ApiResponseType(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
