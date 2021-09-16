package com.ssg.redisdemo;

import com.ssg.redisdemo.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;

@EnableCaching
@SpringBootApplication
public class RedisDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private MoneyTransferService service;

	@Override
	public void run(String... args) throws Exception {

		// initialize few accounts
		this.redisTemplate.opsForHash().put("account", "a", "100");
		this.redisTemplate.opsForHash().put("account", "b",  "20");

		// transfer money with lua script
		this.service.transfer("a", "b", 20);

		// check the results
		System.out.println(
				this.redisTemplate.opsForHash().get("account", "a")
		);
		System.out.println(
				this.redisTemplate.opsForHash().get("account", "b")
		);
	}
}
