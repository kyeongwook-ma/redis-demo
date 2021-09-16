package com.ssg.redisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.IntStream;

@EnableCaching
@SpringBootApplication
public class RedisDemoApplication implements CommandLineRunner {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        piplineSet();
    }

    void piplineSet() {
        String key = "key";
        long start = System.currentTimeMillis();

        List<Object> results = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
			IntStream.range(0, 200).forEach(
					i -> connection.listCommands().rPush(key.getBytes(StandardCharsets.UTF_8),
							String.valueOf(i).getBytes(StandardCharsets.UTF_8))
			);

			return null;
		});

        System.out.println("pipelined SET elpased time(ms) " + (System.currentTimeMillis() - start));

        System.out.println(results);
    }

    void set() {
        String key = "key";
        long start = System.currentTimeMillis();

        IntStream.range(0, 200).forEach(
                i -> redisTemplate.opsForList().rightPush(key, String.valueOf(i))
        );

        System.out.println("SET elpased time(ms) " + (System.currentTimeMillis() - start));
    }
}
