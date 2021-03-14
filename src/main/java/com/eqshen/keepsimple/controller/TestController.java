package com.eqshen.keepsimple.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eqshen
 * @description
 * @date 2021/3/11
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("hello")
    public String hello(String name){
        String res = "hello " + name;
        System.out.println(res);
        return res;
    }
}
