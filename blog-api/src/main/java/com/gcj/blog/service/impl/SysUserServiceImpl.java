package com.gcj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gcj.blog.dao.SysUserMapper;
import com.gcj.blog.pojo.SysUser;
import com.gcj.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

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
}
