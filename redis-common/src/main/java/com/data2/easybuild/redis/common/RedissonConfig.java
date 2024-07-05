package com.data2.easybuild.redis.common;

import lombok.Data;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author data2
 * @description
 * @date 2021/3/20 上午10:49
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "easy.redis.redisson.config")
public class RedissonConfig extends Config {
}
