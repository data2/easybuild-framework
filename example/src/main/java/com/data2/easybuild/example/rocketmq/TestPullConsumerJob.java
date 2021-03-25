package com.data2.easybuild.example.rocketmq;

import com.data2.easybuild.mq.common.rocketmq.Consumer;
import com.data2.easybuild.mq.common.rocketmq.PullConsumerJob;
import org.springframework.stereotype.Component;

/**
 * @author data2
 * @description
 * @date 2021/3/25 下午4:03
 */
@Component
@Consumer(consumerGroup = "test_consumer_group", topic = "test_topic", namesrvAddr = "")
public class TestPullConsumerJob extends PullConsumerJob {

    @Override
    public void run() {
        // DO your business, with consumer
        //consumer.fetchConsumeOffset();
    }
}
