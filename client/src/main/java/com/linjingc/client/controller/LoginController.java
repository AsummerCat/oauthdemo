package com.linjingc.client.controller;

import com.linjingc.client.utils.OAuthClientUtil;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class LoginController {
    @Autowired
    private OAuthClientUtil oAuthClientUtil;

    /**
     * 登录授权页
     *
     * @return
     */
    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String apiToken;
        try {
            apiToken = oAuthClientUtil.getApiToken(username, password);
        } catch (OAuthProblemException e) {
            //重置response
            response.reset();
            //设置编码格式
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(e.getDescription());
            pw.flush();
            pw.close();
            return "";
        }
        request.getSession(false).setAttribute("token",apiToken);
    return  "hello";
    }

    @GetMapping("/testPath")
    public String testPath() {
        return "欢迎";
    }


}
