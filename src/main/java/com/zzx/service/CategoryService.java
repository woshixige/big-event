package com.zzx.service;

import com.zzx.pojo.Category;

import java.util.List;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-08 16:40
 */
public interface CategoryService {
    //添加分类
    void add(Category category);
    //文章列表
    List<Category> list();
    //根据id查询文章分类
    Category findById(Integer id);
    //根据id更改文章分类
    void update(Category category);
    //根据id删除文章分类
    void delete(Integer id);
}
