package com.gcj.blog.service;

import com.gcj.blog.vo.ArticleVo;
import com.gcj.blog.vo.R;
import com.gcj.blog.vo.param.PageParam;

import java.util.List;

public interface ArticleService {
    List<ArticleVo> listArticle(PageParam pageParam);

    R hot(int limit);

    R listArchives();

    R newArticle(int limit);

}
