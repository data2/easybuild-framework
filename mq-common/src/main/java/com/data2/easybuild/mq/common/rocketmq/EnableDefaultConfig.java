package com.data2.easybuild.mq.common.rocketmq;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午5:58
 */
@Configuration
@EnableConfigurationProperties({RocketMqConsumerConfig.class,RocketMqProducerConfig.class})
public class EnableDefaultConfig {
}
