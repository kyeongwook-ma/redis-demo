package com.ssg.redisdemo.repository;

import com.ssg.redisdemo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
