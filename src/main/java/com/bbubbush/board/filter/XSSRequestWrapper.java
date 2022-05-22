package com.bbubbush.board.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
public class XSSRequestWrapper extends HttpServletRequestWrapper {

  private String bufferOfRequestContent;

  protected XSSRequestWrapper(HttpServletRequest request) {
    super(request);
    try {
      bufferOfRequestContent = request.getReader()
        .lines()
        .collect(Collectors.joining());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String doFilter() {
    return this.bufferOfRequestContent
      .replaceAll("<", "%lt;")
      .replaceAll(">", "%gt;");
  }

  @Override
  public ServletInputStream getInputStream() {
    return new StringInputStream(this.doFilter());
  }

  @Override
  public BufferedReader getReader() {
    return new BufferedReader(new InputStreamReader(this.getInputStream()));
  }

}
