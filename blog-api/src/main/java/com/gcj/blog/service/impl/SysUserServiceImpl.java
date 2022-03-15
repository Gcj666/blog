package com.gcj.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gcj.blog.dao.SysUserMapper;
import com.gcj.blog.pojo.SysUser;
import com.gcj.blog.service.SysUserService;
import com.gcj.blog.utils.JWTUtils;
import com.gcj.blog.vo.ErrorCode;
import com.gcj.blog.vo.LoginUserVo;
import com.gcj.blog.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private SysUserMapper sysUserMapper;

    //查询文章作者，一对一
    @Override
    public SysUser findAuthorByArticleId(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        //如果这个文章没有作者。就设置一个默认的作者名称
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("高晨钧！");
        }
        return sysUser;
    }
    //从数据库中查询用户是否存在
    @Override
    public SysUser findUser(String username, String pwd) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        //select id,account.avatar,nickname from ms_sys_user where account = username and password = pwd
        queryWrapper.eq(SysUser::getAccount,username);
        //存入数据库中的密码是加密后的，所以在loginService中进行密码加密
        queryWrapper.eq(SysUser::getPassword,pwd);
        queryWrapper.select(SysUser::getId,SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
        //为了提高sql语句效率，查到第一个就返回
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }
    //获取当前用户信息
    @Override
    public R getCurrentUser(String token) {
        /**
         * 1、先通过jwt工具类，检查token
         * 2、如果存在，在redis中进行二次检测，防止伪造token
         * 3、如果检测通过，从redis中取出user的json字符串，然后转成对象，赋值给vo返回前端
         */
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return R.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        String userInfo = redisTemplate.opsForValue().get("TOKEN_"+token);
        if (userInfo == null){
            return R.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        //将redis中的json字符串转换成sysuser对象
        SysUser sysUser = JSON.parseObject(userInfo, SysUser.class);
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());


        return R.success(loginUserVo);
    }


}
