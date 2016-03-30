package com.demo.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.demo.util.FastJson;
import com.demo.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        if (WebUtils.isLoginUri(uri) || WebUtils.isLogined()) {
            return true;
        }
        HttpSession session = request.getSession();
        if (WebUtils.isAjaxRequest(request)) {
            PrintWriter writer = response.getWriter();
            Map<String, Object> result = new HashMap<String, Object>();
            session.setAttribute("redirectUrl", request.getHeader("Referer"));
            result.put("errCode", 0);
            writer.write(FastJson.toJson(result));
            writer.flush();
            return false;
        }
        session.setAttribute("redirectUrl", WebUtils.buildReturnUrl(request));
        response.sendRedirect("/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
