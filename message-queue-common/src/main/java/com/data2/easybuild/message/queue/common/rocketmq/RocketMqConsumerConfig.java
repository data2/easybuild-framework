package com.data2.easybuild.message.queue.common.rocketmq;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午5:53
 */
@Data
@ConfigurationProperties(prefix = "easy.rocketmq.consumer")
@ConditionalOnExpression("!T(org.springframework.util.StringUtils).isEmpty('${easy.rocketmq.consumer.nameSrvAddr:}')")
public class RocketMqConsumerConfig extends RocketConfig{
}


