package com.data2.easybuild.server.common.lock;

import com.data2.easybuild.api.common.utils.ParamUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

/**
 * @author data2
 * @description
 * @date 2021/1/28 下午5:00
 */
@Slf4j
@Configuration
@Data
@ConfigurationProperties(prefix = "easy.redis")
public class RedisConfig {

    private String host;
    private Integer port;
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;

    @Bean
    public GenericObjectPoolConfig config() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(ParamUtil.isNull(maxIdle, 1000));
        config.setMaxTotal(ParamUtil.isNull(maxTotal, 1000));
        config.setMinIdle(ParamUtil.isNull(minIdle, 1000));
        return config;
    }

    @Bean
    public JedisPool jedisPool() {
        if (StringUtils.isEmpty(host) || port == null) {
            log.info("redis host or port is not config, if u use jedispool will throw nullpointexception!");
            return null;
        }
        return new JedisPool(config(), host, port);
    }
}
