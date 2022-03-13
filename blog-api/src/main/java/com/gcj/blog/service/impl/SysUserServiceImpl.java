package com.gcj.blog.service.impl;

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
}
