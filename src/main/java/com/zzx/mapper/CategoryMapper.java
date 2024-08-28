package com.zzx.mapper;

import com.zzx.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-08 16:40
 */
@Mapper
public interface CategoryMapper {
    //添加分类
    @Insert("insert into category(category_name,category_alias, create_user, create_time, update_time)" +
            " values (#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);
    //查询文章列表
    @Select("select * from category where create_user=#{userId}")
    List<Category> list(Integer userId);
    //根据id查询文章分类
    @Select("select * from category where id =#{id}")
    Category findById(Integer id);
    //根据id更改文章分类
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias}," +
            "update_time=now() where id=#{id}")
    void update(Category category);
    //根据id删除文章分类
    @Delete("delete from category where id=#{id}")
    void delete(Integer id);
}
