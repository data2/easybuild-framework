package com.data2.easybuild.message.queue.common.rocketmq;

import com.data2.easybuild.api.common.exception.EasyBusinessException;
import com.data2.easybuild.api.common.utils.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午4:59
 */
@Slf4j
@Component
public class LoadThread extends ConsumerLoader {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> cls = bean.getClass();
        Annotation[] annotations = cls.getAnnotations();
        if (Objects.nonNull(annotations)){
            return bean;
        }
        if (ArrayUtils.contains(annotations, RocketMqConsumer.class)) {
            RocketMqConsumer annotation = cls.getAnnotation(RocketMqConsumer.class);
            RocketMqConsumerConfig rocketMqConsumerConfig = applicationContext.getBean(RocketMqConsumerConfig.class);
            String topic = annotation.topic(), namesrvAddr = annotation.namesrvAddr(), consumerGroup = annotation.consumerGroup();
            if (StringUtils.isEmpty(topic)) {
                if (StringUtils.isNotEmpty(rocketMqConsumerConfig.getTopic())) {
                    topic = rocketMqConsumerConfig.getTopic();
                    log.warn("consumer program use default consumer-topic config from yml!");
                } else {
                    throw new EasyBusinessException("consumer topic must config, please check your configuration!");
                }
            }
            if (StringUtils.isEmpty(namesrvAddr)) {
                if (StringUtils.isNotEmpty(rocketMqConsumerConfig.getNamesrvAddr())) {
                    namesrvAddr = rocketMqConsumerConfig.getNamesrvAddr();
                    log.warn("consumer program use default consumer-NameSrvAddr config from yml!");
                } else {
                    throw new EasyBusinessException("consumer NameSrvAddr must config, please check your configuration!");
                }
            }
            if (StringUtils.isEmpty(consumerGroup)) {
                if (StringUtils.isNotEmpty(rocketMqConsumerConfig.getConsumerGroup())) {
                    consumerGroup = rocketMqConsumerConfig.getConsumerGroup();
                    log.warn("consumer program use default consumer-consumerGroup config from yml!");
                } else {
                    throw new EasyBusinessException("consumer consumerGroup must config, please check your configuration!");
                }
            }
            check(consumerGroup);
            if (bean instanceof AbstractPullConsumerJob) {
                DefaultLitePullConsumer defaultLitePullConsumer = new DefaultLitePullConsumer(consumerGroup);
                defaultLitePullConsumer.setNamesrvAddr(namesrvAddr);
                try {
                    defaultLitePullConsumer.subscribe(topic,annotation.tag());
                } catch (MQClientException e) {
                    throw new EasyBusinessException("AbstractPullConsumerJob subscribe topic except!");
                }
                ((AbstractPullConsumerJob) bean).setConsumer(defaultLitePullConsumer);
                try {
                    defaultLitePullConsumer.start();
                } catch (MQClientException e) {
                    throw new EasyBusinessException("AbstractPullConsumerJob start except!");
                }
            }
            if (bean instanceof AbstractPushConsumerJob) {
                DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
                defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
                MessageListener messageListener = (MessageListener) applicationContext.getBean(annotation.listener());
                try {
                    defaultMQPushConsumer.subscribe(topic, annotation.tag());
                    if (messageListener instanceof MessageListenerConcurrently){
                        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently)messageListener);
                    }else if (messageListener instanceof MessageListenerOrderly){
                        defaultMQPushConsumer.registerMessageListener((MessageListenerOrderly)messageListener);
                    }else {
                        defaultMQPushConsumer.registerMessageListener(messageListener);
                    }
                    ((AbstractPushConsumerJob) bean).setConsumer(defaultMQPushConsumer);
                } catch (MQClientException e) {
                    throw new EasyBusinessException("AbstractPushConsumerJob subscribe exception occur");
                }
                try {
                    defaultMQPushConsumer.start();
                } catch (MQClientException e) {
                    throw new EasyBusinessException("AbstractPushConsumerJob start except!");

                }
            }
        }else if (ArrayUtils.contains(annotations, KafkaConsumer.class)) {
            KafkaConsumer annotation = cls.getAnnotation(KafkaConsumer.class);
            KafkaConsumerConfig kafkaConsumerConfig = applicationContext.getBean(KafkaConsumerConfig.class);
            String bootstrapServers = annotation.bootstrapServers(),  groupId = annotation.groupId(), topic = annotation.topic(),
                     keyDeserializer = annotation.keyDeserializer(), valueDeserializer = annotation.valueDeserializer();
            if (StringUtils.isEmpty(bootstrapServers)) {
                if (StringUtils.isNotEmpty(kafkaConsumerConfig.getBootstrapServers())) {
                    bootstrapServers = kafkaConsumerConfig.getBootstrapServers();
                    log.warn("kafka consumer program use default consumer-bootstrapServers config from yml!");
                } else {
                    throw new EasyBusinessException("kafka consumer bootstrapServers must config, please check your configuration!");
                }
            }
            if (StringUtils.isEmpty(groupId)) {
                if (StringUtils.isNotEmpty(kafkaConsumerConfig.getGroupId())) {
                    groupId = kafkaConsumerConfig.getGroupId();
                    log.warn("kafka consumer program use default consumer-groupId config from yml!");
                } else {
                    throw new EasyBusinessException("kafka consumer groupId must config, please check your configuration!");
                }
            }
            if (StringUtils.isEmpty(topic)) {
                if (StringUtils.isNotEmpty(kafkaConsumerConfig.getTopic())) {
                    topic = kafkaConsumerConfig.getTopic();
                    log.warn("kafka consumer program use default consumer-topic config from yml!");
                } else {
                    throw new EasyBusinessException("kafka consumer topic must config, please check your configuration!");
                }
            }
            if (StringUtils.isEmpty(keyDeserializer)) {
                if (StringUtils.isNotEmpty(kafkaConsumerConfig.getKeyDeserializer())) {
                    keyDeserializer = kafkaConsumerConfig.getKeyDeserializer();
                    log.warn("kafka consumer program use default consumer-keyDeserializer config from yml!");
                } else {
                    throw new EasyBusinessException("kafka consumer keyDeserializer must config, please check your configuration!");
                }
            }
            if (StringUtils.isEmpty(valueDeserializer)) {
                if (StringUtils.isNotEmpty(kafkaConsumerConfig.getValueDeserializer())) {
                    valueDeserializer = kafkaConsumerConfig.getValueDeserializer();
                    log.warn("kafka consumer program use default consumer-valueDeserializer config from yml!");
                } else {
                    throw new EasyBusinessException("kafka consumer valueDeserializer must config, please check your configuration!");
                }
            }
            Properties props = new Properties();
            props.put("bootstrap.servers", bootstrapServers);
            props.put("group.id", groupId);
            props.put("key.deserializer", keyDeserializer);
            props.put("value.deserializer", valueDeserializer);

            org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(props);
            consumer.subscribe(Collections.singleton(topic));
            ((AbstractPullConsumerJob) bean).setKafkaConsumer(consumer);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private Object threadPoolWay(Object bean, String beanName) {
        Consumer annotation = bean.getClass().getAnnotation(Consumer.class);
        if (Objects.nonNull(annotation)) {
            if (bean instanceof AbstractPullConsumerJob) {
                try {
                    ((AbstractPullConsumerJob) bean).getConsumer().start();
                    ((ThreadPoolExecutor)applicationContext.getBean("threadPool")).execute((Runnable) bean);
                } catch (MQClientException e) {
                    e.printStackTrace();
                }
            }
            if (bean instanceof AbstractPushConsumerJob){
                try {
                    ((AbstractPushConsumerJob) bean).getConsumer().start();
                } catch (MQClientException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
