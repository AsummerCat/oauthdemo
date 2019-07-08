package com.linjingc.client.config.errormsg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义AuthExceptionEntryPoint
 * 用于token校验失败返回信息
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Value("${basic.errorUrl.unauthorized}")
    private String url;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {

        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}