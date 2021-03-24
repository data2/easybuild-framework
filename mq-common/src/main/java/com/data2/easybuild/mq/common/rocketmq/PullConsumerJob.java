package com.data2.easybuild.mq.common.rocketmq;

import lombok.Data;
import org.apache.rocketmq.client.consumer.MQPullConsumer;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午3:28
 */
@Data
public abstract class PullConsumerJob implements MqConsumer {
    protected MQPullConsumer consumer;

    @Override
    public void destroy() {
        if (Objects.nonNull(consumer)) {
            consumer.shutdown();
        }
    }
}
