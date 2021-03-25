package com.data2.easybuild.mq.common.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author data2
 * @description
 * @date 2021/3/25 下午11:27
 */
@Configuration
public class EasyProducerConfiguration {
    public static final String SUBPREFIX = "easy.rocketmq.producer.transaction";
    @Autowired
    private RocketMqProducerConfig rocketMqProducerConfig;
    @Bean
    @ConditionalOnProperty(prefix = SUBPREFIX, name = "enable", havingValue = "false")
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(rocketMqProducerConfig.getGroup());
        defaultMQProducer.setNamesrvAddr(rocketMqProducerConfig.getNameSrvAddr());
        defaultMQProducer.start();
        return defaultMQProducer;
    }

    @Bean
    @ConditionalOnProperty(prefix = SUBPREFIX, name = "enable", havingValue = "true")
    public TransactionMQProducer transactionMQProducer() throws MQClientException {
        TransactionMQProducer transactionMQProducer = new TransactionMQProducer(rocketMqProducerConfig.getGroup());
        transactionMQProducer.setNamesrvAddr(rocketMqProducerConfig.getNameSrvAddr());
        transactionMQProducer.start();
        return transactionMQProducer;
    }
}
