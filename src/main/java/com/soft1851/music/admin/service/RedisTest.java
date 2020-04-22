package com.soft1851.music.admin.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wl
 * @ClassNameRedisTest
 * @Description 第一种序列化方案为为默认序列化方案
 * 第二种序列化方案为string序列化方案
 * @Date 2020/4/21
 * @Version 1.0
 */
@Service
public class RedisTest {
//    @Resource
//    //默认序列化方案要改
//    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    public void testRedis(){
        ValueOperations<String ,String>ops=stringRedisTemplate.opsForValue();
        ops.set("niit","soft");
        System.out.println(ops.get("niit"));

    }
}
