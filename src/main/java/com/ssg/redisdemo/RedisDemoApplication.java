package com.ssg.redisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.IntStream;

@EnableCaching
@SpringBootApplication
public class RedisDemoApplication implements CommandLineRunner {

	@Autowired
	RedisTemplate<String, String> redisStringOperations;

	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}

	protected void pipelineSet() {
		long start = System.currentTimeMillis();
		String key = "key";

		List<Object> results = redisStringOperations.executePipelined(
				(RedisCallback<Object>) connection -> {

					IntStream.range(0, 100)
							.forEach(i -> connection.listCommands().rPush(key.getBytes(StandardCharsets.UTF_8),
									String.valueOf(i).getBytes(StandardCharsets.UTF_8)));
					return null;
				});

		System.out.println(results);

		System.out.println("Pipeline SET elapsed time(ms) -> "+(System.currentTimeMillis() - start));
	}

	protected void set() {
		String key = "key2";
		long start = System.currentTimeMillis();
		try {
			IntStream.range(0, 100).forEach(i -> redisStringOperations.opsForList()
					.rightPush(key, String.valueOf(i)));
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("SET elapsed time(ms) -> "+(System.currentTimeMillis() - start));
	}


	@Override
	public void run(String... args) throws Exception {
		pipelineSet();
		set();
	}
}
