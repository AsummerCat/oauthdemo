package com.linjingc.authentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cxc
 * 错误自定义页面
 */
@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping(value = "/400")
    public String error400Page(Model model) {
        model.addAttribute("code","400");
        model.addAttribute("msg","错误请求");
        return "error";
    }
    @GetMapping(value = "/401")
    public String error401Page(Model model) {
        model.addAttribute("code","401");
        model.addAttribute("msg","暂无权限");
        return "error";
    }
    @GetMapping(value = "/404")
    public String error404Page(Model model) {
        model.addAttribute("code","404");
        model.addAttribute("msg","没有找到该页面");
        return "error";
    }
    @GetMapping(value = "/500")
    public String error500Page(Model model) {
        model.addAttribute("code","500");
        model.addAttribute("msg","服务器错误..");
        return "error";
    }
}