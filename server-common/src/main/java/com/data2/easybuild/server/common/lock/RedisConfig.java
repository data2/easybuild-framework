package com.data2.easybuild.server.common.lock;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @author data2
 * @description
 * @date 2021/1/28 下午5:00
 */
@Configuration
@ConfigurationProperties(prefix = "easy.redis")
public class RedisConfig {

    private String host;
    private int port;
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;

    @Bean
    public GenericObjectPoolConfig config() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        return config;
    }

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool(config(), host, port);
    }
}
