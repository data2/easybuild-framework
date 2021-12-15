package com.data2.easybuild.example.rocketmq;

import com.data2.easybuild.message.queue.common.rocketmq.AbstractPullConsumerJob;
import com.data2.easybuild.message.queue.common.rocketmq.RocketMqConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author data2
 * @description
 * @date 2021/3/25 下午4:03
 */
@Component
@RocketMqConsumer(consumerGroup = "test_consumer_group", topic = "test_topic", namesrvAddr = "")
public class TestPullConsumerJob extends AbstractPullConsumerJob {

    @Override
    public void run(String... args) {
        while (true) {
            try {
                // DO your business, with consumer
                List<MessageExt> messExts = consumer.poll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
