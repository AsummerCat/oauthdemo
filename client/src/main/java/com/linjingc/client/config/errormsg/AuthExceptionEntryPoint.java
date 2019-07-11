package com.linjingc.client.config.errormsg;

import com.linjingc.client.utils.OAuthClientUtil;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义AuthExceptionEntryPoint
 * 用于token校验失败返回信息
 */
public class AuthExceptionEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Value("${basic.errorUrl.unauthorized}")
    private String url;
    private WebResponseExceptionTranslator<?> exceptionTranslator = new DefaultWebResponseExceptionTranslator();
    @Autowired
    private OAuthClientUtil oAuthClientUtil;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {
        try {

            //解析异常，如果是401则处理
            ResponseEntity<?> result = exceptionTranslator.translate(authException);
            if (result.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                try {
                    String token = oAuthClientUtil.refreshToken(request.getSession().getAttribute("token").toString());
                    request.getSession(false).setAttribute("token", token);
                    request.getRequestDispatcher(request.getRequestURI()).forward(request, response);

                } catch (OAuthProblemException e) {
                    System.out.println(e.toString());
                    response.sendRedirect(url);
                }

            } else {
                //如果不是401异常，则以默认的方法继续处理其他异常
                super.commence(request, response, authException);
            }

        } catch (Exception e) {
            throw new ServletException();
        }
    }
}