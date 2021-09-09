package com.data2.easybuild.redis.common;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author data2
 * @description
 * @date 2021/3/20 下午1:50
 */
@Configuration
public class RedissonConfiguration {

    private static final String TEST_STR = "1111";

    @Autowired
    private RedissonConfig redissonConfig;

    @Bean
    @ConditionalOnClass(RedissonConfig.class)
    public RedissonClient redissonClient(){
        RedissonClient client = Redisson.create(redissonConfig);
        client.getAtomicLong(TEST_STR);
        return client;
    }
}
