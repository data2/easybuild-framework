package com.data2.easybuild.mq.common.rocketmq;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午3:28
 */
@Data
@Slf4j
public abstract class PushConsumerJob implements MqConsumer {
    protected MQPushConsumer consumer;

    @Override
    public void destroy() {
        if (Objects.nonNull(consumer)) {
            consumer.shutdown();
            log.info("PushConsumerJob shutdown!");
        }
    }

    @Override
    public void run() {
    }
}
