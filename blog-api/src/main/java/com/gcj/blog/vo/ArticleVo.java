package com.gcj.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    private int id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;

    private String createDate;

    private String author;

    //private ArticleBodyVo body;

    private List<TagVo> tags;

    //private List<CategoryVo> categorys;
}
