package com.zzx;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-12 19:32
 */
@SpringBootTest//如果在该类上写了该注解，就会初始化spring容器
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void testSet(){
        //在redis种存储一个键值对
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("username","jack");
        ops.set("id","1",10,TimeUnit.SECONDS);
    }
    @Test
    public void testGet(){
        String username = stringRedisTemplate.opsForValue().get("username");
        System.out.println(username);
    }
}
