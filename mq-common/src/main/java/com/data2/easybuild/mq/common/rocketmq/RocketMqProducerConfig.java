package com.data2.easybuild.mq.common.rocketmq;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午5:53
 */
@Data
@ConfigurationProperties(prefix = "easy.rocketmq.producer")
@ConditionalOnExpression("!T(org.springframework.util.StringUtils).isEmpty('${easy.rocketmq.producer.nameSrvAddr:}')")
public class RocketMqProducerConfig {
    public String nameSrvAddr;
    public String group;
    public String topic;
    public String tag;
}


