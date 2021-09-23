package com.ssg.redisdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyTransferService {
    @Autowired
    private RedisScript<Boolean> script;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void transfer(String fromAccount, String toACcount, int amount) {
        redisTemplate.execute(script, List.of(fromAccount, toACcount), String.valueOf(amount));
    }
}
