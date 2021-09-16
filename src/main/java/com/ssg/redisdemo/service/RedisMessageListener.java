package com.ssg.redisdemo.service;

import org.springframework.data.redis.connection.MessageListener;

public interface RedisMessageListener extends MessageListener {
    String topic();
}
