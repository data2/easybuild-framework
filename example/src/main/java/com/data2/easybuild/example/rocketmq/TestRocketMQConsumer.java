package com.data2.easybuild.example.rocketmq;

import com.data2.easybuild.mq.common.rocketmq.Consumer;
import com.data2.easybuild.mq.common.rocketmq.PushConsumerJob;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午3:49
 */
@Component
@Consumer(consumerGroup = "test_consumer_group", topic = "test_topic", namesrvAddr = "", listener = TestMessageListener.class)
public class TestRocketMQConsumer extends PushConsumerJob {
}
