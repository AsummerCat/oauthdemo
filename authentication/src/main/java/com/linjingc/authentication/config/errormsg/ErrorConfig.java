package com.linjingc.authentication.config.errormsg;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


/**
 * 自定义错误页面跳转
 * @author cxc
 */
@Component
public class ErrorConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/" + HttpStatus.BAD_REQUEST.value());
        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/" + HttpStatus.UNAUTHORIZED.value());
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/" + HttpStatus.NOT_FOUND.value());
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/" + HttpStatus.INTERNAL_SERVER_ERROR.value());
        registry.addErrorPages(error400Page, error401Page, error404Page, error500Page);
    }
}