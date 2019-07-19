package com.linjingc.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入口
 * @author cxc
 * @date  2019年7月11日16:38:56
 */
@RestController
public class TestEndpoints {


    @GetMapping("/")
    public String index() {
        return "进入认证服务器";
    }

    @GetMapping("/testPath")
    public String testPath() {
        return "欢迎";
    }
}