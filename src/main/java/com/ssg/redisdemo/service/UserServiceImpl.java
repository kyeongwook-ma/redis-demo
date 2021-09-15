package com.ssg.redisdemo.service;

import com.ssg.redisdemo.entity.User;
import com.ssg.redisdemo.repository.UserRepository;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReactiveRedisTemplate<String, User> redisOperations;

    public UserServiceImpl(UserRepository userRepository, ReactiveRedisTemplate<String, User> redisOperations) {
        this.userRepository = userRepository;
        this.redisOperations = redisOperations;
    }

    @Override
    public Flux<User> getAll() {
        return Flux.defer(() -> Flux.fromIterable(userRepository.findAll()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<User> get(String userId) {
        return redisOperations.opsForValue().get(userId)
                .switchIfEmpty(Mono.defer(() -> Mono.justOrEmpty(userRepository.findById(userId)))
                        .subscribeOn(Schedulers.boundedElastic())
                        .doOnNext(user -> redisOperations.opsForValue().set(userId, user).subscribe())
                );
    }
}
