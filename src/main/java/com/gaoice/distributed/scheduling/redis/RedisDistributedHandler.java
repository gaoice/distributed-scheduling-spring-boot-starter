package com.gaoice.distributed.scheduling.redis;

import com.gaoice.distributed.scheduling.DistributedHandler;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author gaoice
 */
public class RedisDistributedHandler implements DistributedHandler {

    StringRedisTemplate stringRedisTemplate;

    public RedisDistributedHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean lock(String key, long timeout, TimeUnit unit) {
        Boolean isAbsent = stringRedisTemplate.opsForValue().setIfAbsent(key, "", timeout, unit);
        return Boolean.TRUE.equals(isAbsent);
    }
}
