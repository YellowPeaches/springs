package com.xyq.shiro3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String login1(){
        return "login";
    }

    @RequestMapping("/login.html")
    public String login2(){
        return "login";
    }

    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }



}
