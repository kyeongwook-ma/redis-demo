package com.ssg.redisdemo.service;

import com.ssg.redisdemo.entity.User;
import com.ssg.redisdemo.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable("users")
    @Override
    public List<User> getAll() {
        redisTemplate.opsForValue().increment("user-get", 1);

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> get(Long userId) {
        return userRepository.findById(userId);
    }
}
