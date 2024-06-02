package org.edu.abhi.redis_demo.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.data")
@Getter
@Setter
public class RedisConfiguration {

    private RedisProperties redis;

    private GenericObjectPoolConfig<redis.clients.jedis.Jedis> poolConfig;

    @PostConstruct
    private void setPoolConfig() {
        poolConfig = new GenericObjectPoolConfig<>();

        RedisProperties.Pool pool = redis.getJedis().getPool();

        poolConfig.setMaxIdle(pool.getMaxIdle());
        poolConfig.setMinIdle(pool.getMinIdle());
        poolConfig.setMaxTotal(pool.getMaxActive());
        poolConfig.setMaxWait(pool.getMaxWait());
    }

}
