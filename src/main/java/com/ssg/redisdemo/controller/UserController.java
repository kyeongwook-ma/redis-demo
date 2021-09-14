package com.ssg.redisdemo.controller;

import com.ssg.redisdemo.entity.User;
import com.ssg.redisdemo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Mono<ResponseEntity<List<User>>> getAllUser() {
        return userService.getAll()
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/users/{userId}")
    public Mono<ResponseEntity<User>> getUser(@PathVariable String userId) {
        return userService.get(userId)
                .map(ResponseEntity::ok);
    }

}
