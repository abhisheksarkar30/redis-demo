package org.edu.abhi.redis_demo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.abhi.redis_demo.util.RedisCacheUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class RedisController {

    private RedisCacheUtil redisCacheUtil;

    @GetMapping("/setentry")
    public void setEntry(@RequestParam String key, @RequestParam String value) {
        redisCacheUtil.setSingleValueEntry(key, value);
    }

    @GetMapping("/getentry")
    public String getEntry(@RequestParam String key) {
        return redisCacheUtil.getSingleValueEntry(key);
    }

    @Cacheable(value = "type1")
    @GetMapping("/testcacheabletype1")
    public String getCacheableType1(@RequestParam String testVal) {
        log.info("Fetching non-cached type1 data {}", testVal);
        return testVal;
    }

    @Cacheable(value = "type2")
    @GetMapping("/testcacheabletype2")
    public String getCacheableType2(@RequestParam String testVal) {
        log.info("Fetching non-cached type2 data {}", testVal);
        return testVal;
    }
}
