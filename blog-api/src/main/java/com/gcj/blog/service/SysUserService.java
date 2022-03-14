package com.gcj.blog.service;


import com.gcj.blog.pojo.SysUser;
import com.gcj.blog.vo.R;

public interface SysUserService {
    SysUser findAuthorByArticleId(Long id);

    SysUser findUser(String username, String pwd);

    R getCurrentUser(String token);
}
