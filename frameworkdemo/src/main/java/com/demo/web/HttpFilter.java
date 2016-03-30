package com.demo.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description
 * @author: holysky
 * @date: 2015/8/11 16:07
 * Copyright © 2015/8/11 Shanghai Raxtone Software Co.,Ltd Allright Reserved
 */
public class HttpFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse resp = (HttpServletResponse) servletResponse;
    try {
      RequestContext.begin(null, req, resp);
      filterChain.doFilter(servletRequest, servletResponse);
    } finally {
      if (RequestContext.get() != null) {
        RequestContext.get().end();
      }
    }
  }



  @Override
  public void destroy() {

  }
}
