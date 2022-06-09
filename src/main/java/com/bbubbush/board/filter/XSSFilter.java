package com.bbubbush.board.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class XSSFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if (!validateUseXSSFilter(request)) {
      filterChain.doFilter(request, response);
      return;
    }
    filterChain.doFilter(new XSSRequestWrapper(request), response);
  }

  private boolean validateUseXSSFilter(HttpServletRequest request) {
    return HttpMethod.POST.name().equals(request.getMethod())
      && MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType());
  }

}
