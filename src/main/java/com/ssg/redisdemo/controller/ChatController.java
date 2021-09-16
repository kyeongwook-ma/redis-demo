package com.ssg.redisdemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.redisdemo.entity.ChatMessage;
import com.ssg.redisdemo.service.RedisMessageListener;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ChatController implements RedisMessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    public ChatController(ObjectMapper objectMapper, RedisTemplate<String, String> redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/api/chat")
    public void publishMessage(@RequestBody ChatMessage chatMessage) throws JsonProcessingException {
        redisTemplate.convertAndSend(topic(), objectMapper.writeValueAsString(chatMessage));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(message.getBody(), ChatMessage.class);
            System.out.println(chatMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String topic() {
        return "topic";
    }
}
