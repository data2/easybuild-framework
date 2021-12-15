package com.data2.easybuild.message.queue.common.rocketmq;

import lombok.Data;

@Data
public class RocketConfig {
    private String namesrvAddr;
    private String consumerGroup;
    private String topic;
    private String tag;
}
