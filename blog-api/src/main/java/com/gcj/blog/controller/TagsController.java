package com.gcj.blog.controller;

import com.gcj.blog.service.TagsService;
import com.gcj.blog.vo.R;
import com.gcj.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@CrossOrigin
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @GetMapping("hot")
    public R hot(){
        //默认显示最热的前六个标签
        int limit = 6;
        List<TagVo> tagVoList = tagsService.getHot(limit);
        return R.success(tagVoList);
    }
}
