package com.gcj.blog.controller;

import com.gcj.blog.service.LoginService;
import com.gcj.blog.vo.R;
import com.gcj.blog.vo.param.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public R login(LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
