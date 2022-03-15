package com.gcj.blog.controller;

import com.gcj.blog.service.LoginService;
import com.gcj.blog.vo.R;
import com.gcj.blog.vo.param.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public R login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
