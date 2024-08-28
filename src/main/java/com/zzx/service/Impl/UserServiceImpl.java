package com.zzx.service.Impl;

import com.zzx.mapper.UserMapper;
import com.zzx.pojo.User;
import com.zzx.service.UserService;
import com.zzx.utils.Md5Util;
import com.zzx.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-07 10:31
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByName(String username) {
        User u=userMapper.findByName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        String md5String = Md5Util.getMD5String(password);
        userMapper.add(username,md5String);
    }

    @Override
    public void update(User user) {
        //设置更新用户的时间
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String new_pwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String pwd = Md5Util.getMD5String(new_pwd);
        userMapper.updatePwd(pwd,id);
    }
}
