package com.gcj.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.gcj.blog.pojo.SysUser;
import com.gcj.blog.service.LoginService;
import com.gcj.blog.service.SysUserService;
import com.gcj.blog.utils.JWTUtils;
import com.gcj.blog.vo.ErrorCode;
import com.gcj.blog.vo.R;
import com.gcj.blog.vo.param.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    //密码加密用的盐
    private static final String salt = "mszlu!@#";
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public R login(LoginParam loginParam) {
        /**
         * 1、判断前端传来的数据是否为空
         * 2、判断数据库中是否存在
         * 3、不存在：返回错误
         * 4、存在：生成token
         * 5、存入redis，设置过期时间
         */
        String username = loginParam.getAccount();
        String password = loginParam.getPassword();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return R.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //密码加密
        String pwd = DigestUtils.md5Hex(password + salt);
        SysUser sysUser = sysUserService.findUser(username,pwd);
        if (sysUser == null){
            return R.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //存在
        String token = JWTUtils.createToken(sysUser.getId());
        //将生成的token存入redis，token作为key，用户信息（json）转成String作为value，设置过期时间为1天
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);

        return R.success(token);
    }
}
