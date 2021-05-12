package com.xyq.shrio3.controller;

import com.xyq.shrio3.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
//@RequestMapping("/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;


    @RequestMapping("/login")
    public String login(String userName, String password){
        String nn=userName;
        try {
            userService.checkLogin(userName,password);
            System.out.println("----登陆成功");
            return "index";
        }catch (Exception e){
            System.out.println("----登陆失败");
            return "unAuthorized";
        }
    }
}
