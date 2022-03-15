package com.gcj.blog.controller;

import com.gcj.blog.service.LoginService;
import com.gcj.blog.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logout")
@CrossOrigin
public class LogoutController {
    @Autowired
    private LoginService loginService;

    @GetMapping
    public R logout(@RequestHeader("Authorization") String token){

        return loginService.logout(token);
    }
}
