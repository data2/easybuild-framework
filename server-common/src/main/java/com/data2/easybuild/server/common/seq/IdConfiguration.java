package com.data2.easybuild.server.common.seq;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author data2
 * @description
 * @date 2021/3/19 上午11:16
 */
@Configuration
@EnableConfigurationProperties(SnowflakeIdWorker.class)
public class IdConfiguration {
}
