package com.gcj.blog.service;

import com.gcj.blog.vo.R;
import com.gcj.blog.vo.param.LoginParam;

public interface LoginService {
    R login(LoginParam loginParam);

    R logout(String token);
}
