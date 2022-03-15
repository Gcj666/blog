package com.gcj.blog.controller;

import com.gcj.blog.service.SysUserService;
import com.gcj.blog.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取当前用户，token存在请求头中，可以通过@RequestHeader("Authorization")将token取出来
     */
    @GetMapping("currentUser")
    public R getUser(@RequestHeader("Authorization") String token){
        return  sysUserService.getCurrentUser(token);
    }
}
