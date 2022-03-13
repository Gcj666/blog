package com.gcj.blog.service;

import com.gcj.blog.vo.TagVo;

import java.util.List;

public interface TagsService {
    //获取最热标签接口
    List<TagVo> getHot(int limit);

    //根据文章id查询对应的标签，一个文章对应多个标签
    List<TagVo> findTagsByArticleId(long articleId);

}
