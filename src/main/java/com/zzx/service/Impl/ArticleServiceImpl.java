package com.zzx.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzx.mapper.ArticleMapper;
import com.zzx.pojo.Article;
import com.zzx.pojo.PageBean;
import com.zzx.pojo.Result;
import com.zzx.service.ArticleService;
import com.zzx.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-08 19:52
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        //补充成员变量属性值
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        //通过ThreadLocalUtil获取token里面的用户id，用于文章的创建者id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建PageBean对象
        PageBean<Article> pb = new PageBean<>();
        //PageHelper开启分页查询
        PageHelper.startPage(pageNum, pageSize);

        //获取用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId, categoryId, state);
        //Page中提供了方法可以获取PageHelper里面的总页数和分页数
        Page<Article> p = (Page<Article>) as;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void update(Article article) {
        //更新更改的时间
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public Article detail(Integer id) {
        return articleMapper.detail(id);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }

}
