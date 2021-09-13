package com.ssg.redisdemo.service;

import com.ssg.redisdemo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> get(Long userId);
}
