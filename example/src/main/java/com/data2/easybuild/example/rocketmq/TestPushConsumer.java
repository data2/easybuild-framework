package com.data2.easybuild.example.rocketmq;

import com.data2.easybuild.message.queue.common.rocketmq.AbstractPushConsumerJob;
import com.data2.easybuild.message.queue.common.rocketmq.Consumer;
import org.springframework.stereotype.Component;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午3:49
 */
@Component
@Consumer(consumerGroup = "test_consumer_group1", topic = "test_topic", namesrvAddr = "", listener = TestPushMessageListener.class)
public class TestPushConsumer extends AbstractPushConsumerJob {
}
