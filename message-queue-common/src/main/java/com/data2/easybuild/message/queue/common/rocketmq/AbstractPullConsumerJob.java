package com.data2.easybuild.message.queue.common.rocketmq;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.MQPullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午3:28
 * DefaultLitePullConsumer from rocketmq-client.version:4.6.0
 *  contain banlance
 *
 */
@Data
@Slf4j
public abstract class AbstractPullConsumerJob implements MqConsumer {
    protected DefaultLitePullConsumer consumer;
    protected KafkaConsumer kafkaConsumer;

    @Override
    public void destroy() {
        if (Objects.nonNull(consumer)) {
            consumer.shutdown();
            log.warn("{} shutdown!", this.getClass().getName());
        }
        if (Objects.nonNull(kafkaConsumer)) {
            kafkaConsumer.close();
            log.warn("{} close!", this.getClass().getName());
        }
    }


}
