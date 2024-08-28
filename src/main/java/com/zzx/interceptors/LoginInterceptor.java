package com.zzx.interceptors;

import com.zzx.pojo.Result;
import com.zzx.utils.JwtUtil;
import com.zzx.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-07 20:19
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //用户登录成功自动下发令牌
        String token = request.getHeader("Authorization");
        try {
            //获取拦截器对应redis的token值
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            //为空说明token失效了
            if (redisToken==null){
                //在try 中抛异常会执行catch中的内容
                throw new RuntimeException();
            }
            //验证token，解析成功之后就不会抛异常，解析失败不放行
            Map<String, Object> claims = JwtUtil.parseToken(token);

            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            //不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
