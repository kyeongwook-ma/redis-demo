package com.ssg.redisdemo.service;

import com.ssg.redisdemo.entity.User;
import com.ssg.redisdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private ReactiveRedisTemplate<String, User> redisTemplate;

    @Override
    public Flux<User> getAll() {
        return Flux.defer(() -> Flux.fromIterable(userRepository.findAll())).cache();
    }

    @Override
    public Mono<User> get(String userId) {
        return redisTemplate.opsForValue()
                .get(userId)
                .switchIfEmpty(
                        Mono.defer(() -> Mono.justOrEmpty(userRepository.findById(userId)))
                                .subscribeOn(Schedulers.boundedElastic())
                        .doOnSuccess(user -> redisTemplate.opsForValue().set(userId, user).subscribe())
                );
    }
}
