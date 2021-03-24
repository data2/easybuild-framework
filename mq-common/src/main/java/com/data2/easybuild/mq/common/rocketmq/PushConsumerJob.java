package com.data2.easybuild.mq.common.rocketmq;

import lombok.Data;
import org.apache.rocketmq.client.consumer.MQPushConsumer;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午3:28
 */
@Data
public abstract class PushConsumerJob implements MqConsumer {
    protected MQPushConsumer consumer;

    @Override
    public void destroy() {
        if (Objects.nonNull(consumer)) {
            consumer.shutdown();
        }
    }
}
