package com.linjingc.authenticationqq.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 资源服务器的资源
 */
@RestController
@RequestMapping("/qq")
public class QQRestfulApiProviderController {

    @RequestMapping("/info/{qq}")
    public QQAccount info(@PathVariable("qq") String qq){
        return InMemoryQQDatabase.database.get(qq);
    }

    @RequestMapping("fans/{qq}")
    public List<QQAccount> fans(@PathVariable("qq") String qq){
        return InMemoryQQDatabase.database.get(qq).getFans();
    }



}
