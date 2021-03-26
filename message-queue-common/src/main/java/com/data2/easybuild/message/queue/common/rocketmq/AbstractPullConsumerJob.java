package com.data2.easybuild.message.queue.common.rocketmq;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.MQPullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午3:28
 */
@Data
@Slf4j
public abstract class AbstractPullConsumerJob implements MqConsumer {
    protected MQPullConsumer consumer;

    @Override
    public void destroy() {
        if (Objects.nonNull(consumer)) {
            consumer.shutdown();
            log.warn("{} shutdown!", this.getClass().getName());
        }
    }


}
