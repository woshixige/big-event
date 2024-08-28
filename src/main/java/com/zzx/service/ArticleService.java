package com.zzx.service;

import com.zzx.pojo.Article;
import com.zzx.pojo.PageBean;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-08 19:51
 */
public interface ArticleService {
    //新增文章
    void add(Article article);
    //分页查询文章列表数据
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
    //更新文章内容
    void update(Article article);

    //获取文章详情
    Article detail(Integer id);

    //删除文章
    void delete(Integer id);
}
