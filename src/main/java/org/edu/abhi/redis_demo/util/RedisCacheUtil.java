package org.edu.abhi.redis_demo.util;

import jakarta.annotation.PostConstruct;
import org.edu.abhi.redis_demo.configuration.RedisConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class RedisCacheUtil {

    @Autowired
    private RedisConfiguration redisConfig;

    private JedisPool jedisPool;

    private RedisCacheUtil() {}

    @PostConstruct
    public void initPool() {
        jedisPool = new JedisPool(redisConfig.getPoolConfig(), redisConfig.getRedis().getHost(), redisConfig.getRedis().getPort());
    }

    public Jedis getInstance() {
        if(ObjectUtils.isEmpty(jedisPool)) {
            initPool();
        }
        return jedisPool.getResource();
    }

    public boolean ifKeyExists(String key) {
        return getInstance().exists(key);
    }

    public void setSingleValueEntry(String key, String value) {
        getInstance().set(key, value);
    }

    public void setAutoExpiringSingleValueEntry(String key, String value, long ttl) {
        getInstance().setex(key, ttl, value);
    }

    public String getSingleValueEntry(String key) {
        return getInstance().get(key);
    }

    public void addListValueEntry(String key, List<String> values) {
        getInstance().rpush(key, values.toArray(new String[0]));
    }

    public List<String> getListValueEntry(String key) {
        return getInstance().lrange(key, 0, -1);
    }

    public void setMultipleEntries(Map<String, String> keyValuePairs) {
        try (Pipeline pipeline = getInstance().pipelined()) {
            keyValuePairs.forEach(pipeline::set);
            pipeline.sync();
        }
    }

    public Map<String, Object> getMultipleEntries(List<String> keys) {
        try (Pipeline pipeline = getInstance().pipelined()) {
            keys.forEach(pipeline::get);

            List<Object> cacheValues = pipeline.syncAndReturnAll();

            return IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(keys::get, cacheValues::get));
        }
    }

    public void deleteMultipleEntries(String... keys) {
        getInstance().del(keys);
    }

    public void closeInstance() {
        getInstance().close();
    }
}
