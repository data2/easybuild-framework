package com.data2.easybuild.message.queue.common.rocketmq;


import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "easy.kafka.consumer")
@ConditionalOnExpression("!T(org.springframework.util.StringUtils).isEmpty('${easy.kafka.consumer.bootstrapServers:}')")
public class KafkaConsumerConfig extends KafkaConfig{
}
