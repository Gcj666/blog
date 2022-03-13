package com.gcj.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcj.blog.pojo.Tags;

import java.util.List;

public interface TagsMapper extends BaseMapper<Tags> {

    List<Tags> findTagsByArticleId(Long articleId);

    List<Long> findHotsTagIds(int limit);

    List<Tags> findTagsByIds(List<Long> tagsIds);
}
