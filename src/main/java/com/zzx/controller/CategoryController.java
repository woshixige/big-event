package com.zzx.controller;

import com.zzx.pojo.Category;
import com.zzx.pojo.Result;
import com.zzx.service.CategoryService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-08 16:39
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //添加分类
    @PostMapping
    public Result category(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.add(category);
        return Result.success();
    }
    //文章列表
    @GetMapping
    public Result<List<Category>> list(){
        List<Category> cg=categoryService.list();
        return Result.success(cg);
    }
    //根据id查询文章列表
    @GetMapping("/detail")
    public Result<Category> detail(@RequestParam Integer id){
        Category c=categoryService.findById(id);
        return Result.success(c);
    }
    //将传入的json数据进行更改，根据id修改
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }
    //删除文章分类
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        categoryService.delete(id);
        return Result.success();
    }
}
