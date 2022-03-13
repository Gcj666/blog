package com.gcj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gcj.blog.dao.ArticleMapper;
import com.gcj.blog.pojo.Article;
import com.gcj.blog.pojo.SysUser;
import com.gcj.blog.service.ArticleService;
import com.gcj.blog.service.SysUserService;
import com.gcj.blog.service.TagsService;
import com.gcj.blog.pojo.Archives;
import com.gcj.blog.vo.ArticleVo;
import com.gcj.blog.vo.R;
import com.gcj.blog.vo.TagVo;
import com.gcj.blog.vo.param.PageParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    //标签service接口
    @Autowired
    private TagsService tagsService;

    //作者service接口
    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<ArticleVo> listArticle(PageParam pageParam) {
        Page<Article> page = new Page<>(pageParam.getPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
        //升序条件
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        //mybatisplus提供的分页查询方法
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        //获取到查询出来的集合
        List<Article> articles = articlePage.getRecords();
        //前端需要vo数据，所以进行属性拷贝
        List<ArticleVo> articleVoList = copyList(articles,true,true);

        return articleVoList;
    }

    //最热文章
    @Override
    public R hot(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //select id,count from ms_article order by counts limit 5
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.last("limit "+limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return R.success(copyList(articleList,false,false));
    }

    @Override
    public R listArchives() {
        List<Archives> archives = articleMapper.listArchives();
        return R.success(archives);
    }

    //展示最新文章
    @Override
    public R newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //SELECT id , title from ms_article ORDER BY create_date desc
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return R.success(copyList(articleList,false,false));
    }

    //转换集合
    private List<ArticleVo> copyList(List<Article> articles,boolean isTags,boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        //遍历集合，把Article中的元素复制给ArticleVo
        for (Article article : articles) {
            ArticleVo articleVo = copy(article,isTags,isAuthor);
            articleVoList.add(articleVo);
        }
        //返回ArticleVo集合
        return articleVoList;
    }

    //将Article属性拷贝到ArticleVo中
    //判断是否需要查询标签和作者属性
    private ArticleVo copy(Article article,boolean isTags,boolean isAuthor){
        //Spring自带的工具类将Article属性赋值给ArticleVo中
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);

        articleVo.setCreateDate(new DateTime().toString());
        //如果这个文章中包含tag属性
        if (isTags){
            //通过文章的id来查询tag放入到查询结果中
            Long articleId = article.getId();
            List<TagVo> tagVos = tagsService.findTagsByArticleId(articleId);
            articleVo.setTags(tagVos);
        }
        //如果这个文章中包含author属性
       if (isAuthor){
           Long id = article.getId();
           SysUser sysUser = sysUserService.findAuthorByArticleId(id);
           articleVo.setAuthor(sysUser.getNickname());
       }
        //转换日期joda依赖中的方法
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        return articleVo;
    }
}
