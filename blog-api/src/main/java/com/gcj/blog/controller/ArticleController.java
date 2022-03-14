package com.gcj.blog.controller;

import com.gcj.blog.service.ArticleService;
import com.gcj.blog.vo.ArticleVo;
import com.gcj.blog.vo.R;
import com.gcj.blog.vo.param.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public R listArticle(@RequestBody PageParam pageParam){
        List<ArticleVo> articleList = articleService.listArticle(pageParam);
        return R.success(articleList);
    }

    @PostMapping("hot")
    public R hot(){
        //热度前五的文章属于热点文章
        int limit = 5;
        return articleService.hot(limit);
    }

    @PostMapping("listArchives")
    public R listArchives(){

        return articleService.listArchives();
    }
    //最新文章,只显示最新的五条文章
    @PostMapping("new")
    public R newArticle(){
        int limit = 5;

        return articleService.newArticle(limit);
    }
}
