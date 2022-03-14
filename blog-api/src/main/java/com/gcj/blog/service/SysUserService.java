package com.gcj.blog.service;


import com.gcj.blog.pojo.SysUser;

public interface SysUserService {
    SysUser findAuthorByArticleId(Long id);

    SysUser findUser(String username, String pwd);
}
