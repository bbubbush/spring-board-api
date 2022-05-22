package com.bbubbush.board.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class StringInputStream extends ServletInputStream {

  private final ByteArrayInputStream bis;

  public StringInputStream(String requestContent) {
    this.bis = new ByteArrayInputStream(requestContent.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public boolean isFinished() {
    return bis.available() == 0;
  }

  @Override
  public int read() {
    if (isFinished()) {
      return -1;
    }
    return bis.read();
  }

  @Override
  public boolean isReady() {
    return false;
  }

  @Override
  public void setReadListener(ReadListener listener) {
  }

}
