package org.edu.abhi.redis_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

@EnableCaching // Required to work with @Cacheable
@SpringBootApplication
public class RedisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}

	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
		return (builder) -> builder
				.withCacheConfiguration("type1",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10)))
				.withCacheConfiguration("type2",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(15)));
	}

}
