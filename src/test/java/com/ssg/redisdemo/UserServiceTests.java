package com.ssg.redisdemo;

import com.ssg.redisdemo.config.CacheConfig;
import com.ssg.redisdemo.entity.User;
import com.ssg.redisdemo.repository.UserRepository;
import com.ssg.redisdemo.service.UserService;
import com.ssg.redisdemo.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    private final UserRepository userRepository = mock(UserRepository.class);

    private UserService userService;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void givenRedisCaching() {
        User user = new User();
        user.setId(1L);

        given(userRepository.findById(1L))
                .willReturn(Optional.of(user));

        User itemCacheHit = userService.get(1L).orElse(null);

        assertThat(itemCacheHit).isEqualTo(user);

        verify(userRepository, times(1)).findById(1L);
    }
}