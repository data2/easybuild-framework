package com.data2.easybuild.redis.common;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author data2
 * @description
 * @date 2024/7/5 13:46
 */
@Component
@Slf4j
public class DistributeLockDemo {

    @Autowired
    private RedissonClient redissonClient;

    public void lock(String key) {
        RLock lock = redissonClient.getLock(key);
        try {
            lock.tryLock(5000, 10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.warn("interuppted",e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
