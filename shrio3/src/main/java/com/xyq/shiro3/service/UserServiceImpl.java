package com.xyq.shiro3.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    public void checkLogin(String userName, String userPwd) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName,userPwd);
        subject.login(token);
    }
}
