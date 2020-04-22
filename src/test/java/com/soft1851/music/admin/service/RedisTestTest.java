package com.soft1851.music.admin.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RedisTestTest {
@Resource
private  RedisTest redisTest;
    @Test
    void testRedis() {
        redisTest.testRedis();
    }
}