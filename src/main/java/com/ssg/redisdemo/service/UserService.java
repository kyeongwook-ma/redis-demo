package com.ssg.redisdemo.service;

import com.ssg.redisdemo.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getAll();
    Mono<User> get(String userId);
}
