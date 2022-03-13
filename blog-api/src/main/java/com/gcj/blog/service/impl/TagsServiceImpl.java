package com.gcj.blog.service.impl;

import com.gcj.blog.dao.TagsMapper;
import com.gcj.blog.pojo.Tags;
import com.gcj.blog.service.TagsService;
import com.gcj.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsMapper tagsMapper;


    /**
     * 标签拥有的文章最多，就是最热标签
     * 查询根据tag_id 分组计数。从大到小取limit个
     * @param limit
     * @return
     */
    @Override
    public List<TagVo> getHot(int limit) {
        /**
         * 先查出前limit个热点标签的id集合
         */
        List<Long> tagsIds = tagsMapper.findHotsTagIds(limit);
        /**
         * 如果没有就返回一个空集合
         * 再根据id查出具体的标签返回给前端
         */
        if (CollectionUtils.isEmpty(tagsIds)){
            return Collections.emptyList();
        }
        List<Tags> tags = tagsMapper.findTagsByIds(tagsIds);
        List<TagVo> tagVos = copyList(tags);
        return tagVos;
    }

    /**
     * 因为Mybtis-plus不支持多表查询，所以自定义一个方法来查询数据库
     * @param articleId
     * @return
     */
    @Override
    public List<TagVo> findTagsByArticleId(long articleId) {
        List<Tags> tags = tagsMapper.findTagsByArticleId(articleId);
        List<TagVo> tagVos = copyList(tags);
        return tagVos;
    }

    //转换集合方法
    public TagVo copy(Tags tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    //属性赋值方法
    public List<TagVo> copyList(List<Tags> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tags tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }
}
