package com.gcj.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcj.blog.pojo.Article;
import com.gcj.blog.pojo.Archives;

import java.util.List;


public interface ArticleMapper extends BaseMapper<Article> {
    List<Archives> listArchives();

}
