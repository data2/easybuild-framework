package com.data2.easybuild.redis.common;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author data2
 * @description
 * @date 2021/3/20 下午1:50
 */
@Configuration
public class RedissonConfiguration {

    @Bean
    @ConditionalOnClass(RedissonConfig.class)
    public RedissonClient redissonClient(RedissonConfig redissonConfig){
        return Redisson.create(redissonConfig);
    }
}
