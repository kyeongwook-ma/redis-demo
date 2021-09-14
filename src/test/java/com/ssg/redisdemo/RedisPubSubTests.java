package com.ssg.redisdemo;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RedisPubSubTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void contextLoads() {
        redisTemplate.convertAndSend("pubsub", "sending test");
    }

}