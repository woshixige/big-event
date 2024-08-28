package com.zzx.controller;

import com.zzx.pojo.User;
import com.zzx.pojo.Result;
import com.zzx.service.UserService;
import com.zzx.utils.JwtUtil;
import com.zzx.utils.Md5Util;
import com.zzx.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-07 10:27
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //通过名字查询用户
        User u = userService.findByName(username);
        if (u == null) {
            //该用户名没有被注册过，可以注册用户
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("该用户已经被注册");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User loginUser = userService.findByName(username);
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        String loginUserPassword = loginUser.getPassword();
        if (Md5Util.getMD5String(password).equals(loginUserPassword)) {
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            claims.put("password", loginUser.getPassword());
            String token = JwtUtil.genToken(claims);
            //jwt 令牌...
            //将token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            //将储存到redis的token的失效时间和原来设置的token时间一致
            operations.set(token,token,12, TimeUnit.HOURS);

            return Result.success(token);
        }
        return Result.error("密码有误");
    }

    /*    //根据请求头来获取用户的信息
        public Result<User> userInfo(@RequestHeader(name = "Authorization") String token){
            //通过token拿到用户名
            Map<String, Object> map = JwtUtil.parseToken(token);
            String username = (String) map.get("username");
            //通过用户名查询到用户
            User user = userService.findByName(username);
            return Result.success(user);
        }*/
    //根据请求头来获取用户的信息，用ThreadLocal优化代码
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        //通过用户名查询到用户
        User user = userService.findByName(username);
        return Result.success(user);
    }

    //更新用户的基本信息
    @PutMapping("/update")
    public Result update(@RequestBody/*将请求的josn数据转化为java的user对象*/ @Validated User user) {
        userService.update(user);
        return Result.success("数据已成功更新");
    }

    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String token) {
        //获取json对象转化后的数据参数
        String old_pwd = params.get("old_pwd");
        String new_pwd = params.get("new_pwd");
        String re_pwd = params.get("re_pwd");
        //校验参数的合法性，三个参数不为空
        if (!StringUtils.hasLength(old_pwd) || !StringUtils.hasLength(new_pwd) || !StringUtils.hasLength(re_pwd)) {
            return Result.error("密码不能为空");
        }
        //输入的原密码要和用户的密码一致
        //根据ThreadLocal获取token里面的username
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        //根据用户名获取用户
        User user = userService.findByName(username);
        //获取用户的密码
        String password = user.getPassword();//md5加密后的密码
        if (!password.equals(Md5Util.getMD5String(old_pwd))){
            //密码不一致
            return Result.error("输入的原密码与该用户的密码不符");
        }
        if (!new_pwd.equals(re_pwd)){
            //新密码和确认密码不一致
            return Result.error("新密码和确认密码不一致");
        }
        userService.updatePwd(new_pwd);
        //更改密码成功后，删除原来的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success("更改密码成功");
    }
}
