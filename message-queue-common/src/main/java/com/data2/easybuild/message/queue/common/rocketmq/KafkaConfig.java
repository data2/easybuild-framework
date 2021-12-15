package com.data2.easybuild.message.queue.common.rocketmq;

import lombok.Data;

@Data
public class KafkaConfig {
    private String bootstrapServers;
    private String groupId;
    private String topic;
    private String keyDeserializer;
    private String valueDeserializer;
}
