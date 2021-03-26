package com.data2.easybuild.example.rocketmq;

import com.data2.easybuild.message.queue.common.rocketmq.AbstractPullConsumerJob;
import com.data2.easybuild.message.queue.common.rocketmq.Consumer;
import org.springframework.stereotype.Component;

/**
 * @author data2
 * @description
 * @date 2021/3/25 下午4:03
 */
@Component
@Consumer(consumerGroup = "test_consumer_group", topic = "test_topic", namesrvAddr = "")
public class TestPullConsumerJob extends AbstractPullConsumerJob {

    @Override
    public void run(String... args) {
        // DO your business, with consumer
        //consumer.fetchConsumeOffset();
    }
}
