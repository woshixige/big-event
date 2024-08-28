package com.zzx.service.Impl;

import com.zzx.mapper.CategoryMapper;
import com.zzx.pojo.Category;
import com.zzx.service.CategoryService;
import com.zzx.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-08 16:40
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(Category category) {
/*        private Integer createUser;//创建人ID
        private LocalDateTime createTime;//创建时间
        private LocalDateTime updateTime;//更新时间
        private String categoryName;//分类名称*/
        //添加分类需要初始化的属性
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        //通过ThreadLocalUtil获取token里的id
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        category.setCreateUser(userId);
        categoryMapper.add(category);
    }
    //文章列表
    @Override
    public List<Category> list() {
        //根据用户id来查询该文章列表
        Map<String,Object>map=ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        return categoryMapper.list(userId);
    }

    @Override
    public Category findById(Integer id) {
        Category c=categoryMapper.findById(id);
        return c;
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
