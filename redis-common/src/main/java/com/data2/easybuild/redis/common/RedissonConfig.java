package com.data2.easybuild.redis.common;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.data2.easybuild.redis.common.RedissonConfig.PREFIX;


/**
 * @author data2
 * @description
 * @date 2021/3/20 上午10:49
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PREFIX)
public class RedissonConfig extends Config{

    public static final String PREFIX = "easy.redis.redisson.config";

}
