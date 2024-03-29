package com.data2.easybuild.example.rocketmq;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午4:20
 */
@Component
public class TestPushMessageListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        // DO you business
//        if (list != null) {
//            for (MessageExt ext : list) {
//                try {
//                    System.out.println(new String(ext.getBody(), RemotingHelper.DEFAULT_CHARSET));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
