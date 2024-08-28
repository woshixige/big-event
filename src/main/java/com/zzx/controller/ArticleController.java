package com.zzx.controller;

import com.zzx.pojo.Article;
import com.zzx.pojo.PageBean;
import com.zzx.pojo.Result;
import com.zzx.service.ArticleService;
import com.zzx.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-07 19:37
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
/*    @GetMapping("/list")
    public Result<String> list(*//*@RequestHeader(name = "Authorization") String token, HttpServletResponse response*//*){
*//*        try {
            //验证token
            Map<String, Object> claims = JwtUtil.parseToken(token);
            return Result.success("所有的文章数据...");
        } catch (Exception e) {
            response.setStatus(401);
            return Result.error("未登录");
        }*//*
        return Result.success("所有的文章数据...");
    }*/
    @Autowired
    private ArticleService articleService;
    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Article article){
        articleService.update(article);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<Article> detail(Integer id){
        Article article=articleService.detail(id);
        return Result.success(article);
    }
    @DeleteMapping
    public Result delete(Integer id){
        articleService.delete(id);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
        Integer pageNum,
        Integer pageSize,
        //这两个请求参数可以为空
        @RequestParam(required = false) Integer categoryId,
        @RequestParam(required = false) String state
    ){
        PageBean<Article> pb=articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }
}
