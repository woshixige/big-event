package com.zzx.mapper;

import com.zzx.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-08 19:52
 */
@Mapper
public interface ArticleMapper {
    //添加文章
    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, " +
            "create_time, update_time) values (#{title},#{content},#{coverImg},#{state},#{categoryId}," +
            "#{createUser},#{createTime},#{updateTime})")
    void add(Article article);
    //xml文件实现动态查询
    List<Article> list(Integer userId, Integer categoryId, String state);
    //更改文章内容
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg}," +
            "state=#{state},category_id=#{categoryId},update_time=#{updateTime} where id=#{id}")
    void update(Article article);

    //根据文章id获取文章详情
    @Select("select * from article where id=#{id}")
    Article detail(Integer id);

    //删除文章
    @Delete("delete from article where id=#{id}")
    void delete(Integer id);
}
