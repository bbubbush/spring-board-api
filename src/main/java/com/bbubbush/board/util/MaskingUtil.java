package com.bbubbush.board.util;

import org.springframework.util.StringUtils;

public abstract class MaskingUtil {

  private static final String MASKING_CHARACTER = "*";

  /**
   * 입력받은 회원이름을 마스킹합니다. Whitespace를 허용하지 않습니다.
   * 회원이름이 {@code null}이거나 empty이면 입력받은 회원이름을 그대로 반환합니다.
   * JDK 11 이상 버전에서 동작합니다.
   *
   * <p><pre class="code">
   * MaskingUtil.maskUserName(null) = null
   * MaskingUtil.maskUserName("") = ""
   * MaskingUtil.maskUserName("bbubbush") = "b******h"
   * MaskingUtil.maskUserName("bb") = "b*"
   * </pre>
   *
   * @param userName {@code String} 타입으로 {@code null}과 빈 값은 이 기능을 사용할 수 없습니다.
   * @return {@code String} 타입으로 좌우 여백이 제거되고 마스킹된 회원이름을 반환합니다.
   * @see java.lang.String#repeat(int)
   */
  public static String maskUserName(String userName) {
    final String trimUserName = StringUtils.trimAllWhitespace(userName);
    if (!StringUtils.hasLength(trimUserName)) {
      return userName;
    }

    if (trimUserName.length() == 2) {
      return trimUserName.charAt(0) + MASKING_CHARACTER.repeat(1);
    }

    return trimUserName.charAt(0)
      + MASKING_CHARACTER.repeat(userName.length() - 2)
      + trimUserName.substring(trimUserName.length() - 1);
  }

}
