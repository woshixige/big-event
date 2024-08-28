package com.zzx.mapper;

import com.zzx.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-07 10:32
 */
@Mapper
public interface UserMapper {
    //根据名字查询用户
    @Select("select * from user where username=#{username}")
    public User findByName(String username) ;
    //注册用户，将用户数据添加入数据库
    @Insert("insert into user(username, password,create_time, update_time)" +
            " values (#{username},#{password},now(),now())")
    public void add(String username, String password) ;
    //更新用户信息
    @Update("update user set id=#{id},nickname=#{nickname},email=#{email},update_time=#{updateTime}")
    void update(User user);
    //根据用户id更新用户头像
    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);
    //根据用户id更新用户密码
    @Update("update user set password=#{pwd},update_time=now() where id=#{id}")
    void updatePwd(String pwd,Integer id);

}
