package com.linjingc.authentication.config;

import com.linjingc.authentication.utils.HttpUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权失败(forbidden)时返回信息
 * @author cxc
 * @date  2019年7月11日16:38:56
 */
@Component("customAccessDeniedHandler")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // AJAX请求,使用response发送403
        if (HttpUtils.isAjaxRequest(request)) {
            response.sendError(403);
        } else if (!response.isCommitted()) {
            // 非AJAX请求，跳转403错误界面
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    accessDeniedException.getMessage());
        }
    }
}
