package com.zzx.service;

import com.zzx.pojo.User;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-07 10:31
 */
public interface UserService {
    //根据名字查询用户
    User findByName(String username);
    //注册用户
    void register(String username, String password);
    //更新用户信息
    void update(User user);
    //更新头像
    void updateAvatar(String avatarUrl);
    //更新用户密码
    void updatePwd(String new_pwd);
}
