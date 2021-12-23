package com.data2.easybuild.example.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProducerTest {

    //rocketmq分布式事务， 保存本地事物执行结果，提供给mq服务回查。
    Map<String,Boolean> map = new HashMap<>();

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private TransactionMQProducer transactionMQProducer;

    public void send() throws RemotingException, InterruptedException, MQClientException, MQBrokerException {
        String orderId = "100000232131";
        Message message = new Message("topic", "tagA", orderId.getBytes());
        try {
            // 1、同步发送，等待服务响应
            SendResult sendResult = defaultMQProducer.send(message);
        } catch (Exception e) {
        }
        // 2、异步发送，不等待服务响应，但会有回调函数，需要实现
        defaultMQProducer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
            }

            @Override
            public void onException(Throwable throwable) {
            }
        });
        // 3、单向发送，不等服务响应，也没有回调函数
        defaultMQProducer.sendOneway(message);

        // 4、延时消息
        // messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        message.setDelayTimeLevel(2);
        defaultMQProducer.send(message);

        // 5、顺序消息 > 类似于kafka的指定分区路由策略
        String shardingKey = orderId;
        defaultMQProducer.send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                int i = Math.abs(shardingKey.hashCode());
                return list.get(i % list.size());
            }
        }, shardingKey);


        // 6、事物消息
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                String orderid = (String) o;
                // 执行本地事物，成功提交，失败 回滚
                boolean success = executeLocalTrans(orderid);
                return success ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                String orderId = messageExt.getKeys();
                return map.get(orderId)? LocalTransactionState.COMMIT_MESSAGE: LocalTransactionState.ROLLBACK_MESSAGE;
            }
        });
        // 发送half消息
        TransactionSendResult result = transactionMQProducer.sendMessageInTransaction(message, orderId);


    }


    private Boolean executeLocalTrans(String orderid) {
        // do something
        map.put(orderid,true);
        return true;
    }




}
