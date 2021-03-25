package com.data2.easybuild.mq.common.rocketmq;

import com.data2.easybuild.api.common.exception.EasyBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午4:59
 */
@Slf4j
@Component
public class LoadThread implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> cls = bean.getClass();
        Consumer annotation = cls.getAnnotation(Consumer.class);
        if (Objects.nonNull(annotation)) {
            RocketMqConsumerConfig rocketMqConsumerConfig = applicationContext.getBean(RocketMqConsumerConfig.class);
            if (StringUtils.isEmpty(annotation.topic())) {
                if (StringUtils.isNotEmpty(rocketMqConsumerConfig.getTopic())) {
                    log.warn("consumer program use default consumer-topic config from yml!");
                } else {
                    throw new EasyBusinessException("consumer topic must config, please check your configuration!");
                }
            }
            if (StringUtils.isEmpty(annotation.namesrvAddr())) {
                if (StringUtils.isNotEmpty(rocketMqConsumerConfig.getNameSrvAddr())) {
                    log.warn("consumer program use default consumer-nameSrcAddr config from yml!");
                } else {
                    throw new EasyBusinessException("consumer namesrvAddr must config, please check your configuration!");
                }
            }
            if (bean instanceof PullConsumerJob) {
                ((PullConsumerJob) bean).setConsumer(new DefaultMQPullConsumer(annotation.consumerGroup()));
                DefaultMQPullConsumer consumer = (DefaultMQPullConsumer) ((PullConsumerJob) bean).getConsumer();
                consumer.setNamesrvAddr(annotation.namesrvAddr());
            }
            if (bean instanceof PushConsumerJob) {
                ((PushConsumerJob) bean).setConsumer(new DefaultMQPushConsumer(annotation.consumerGroup()));
                DefaultMQPushConsumer consumer = (DefaultMQPushConsumer) ((PushConsumerJob) bean).getConsumer();
                consumer.setNamesrvAddr(annotation.namesrvAddr());
                MessageListener messageListener = (MessageListener) applicationContext.getBean(annotation.listener());
                try {
                    consumer.subscribe(annotation.topic(), annotation.tag());
                    if (messageListener instanceof MessageListenerConcurrently){
                        consumer.registerMessageListener((MessageListenerConcurrently)messageListener);
                    }else if (messageListener instanceof MessageListenerOrderly){
                        consumer.registerMessageListener((MessageListenerOrderly)messageListener);
                    }else {
                        consumer.registerMessageListener(messageListener);
                    }
                } catch (MQClientException e) {
                    e.printStackTrace();
                    throw new EasyBusinessException("PushConsumerJob subscribe exception occur");
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Consumer annotation = bean.getClass().getAnnotation(Consumer.class);
        if (Objects.nonNull(annotation)) {
            if (bean instanceof PullConsumerJob) {
                try {
                    ((PullConsumerJob) bean).getConsumer().start();
                    ((ThreadPoolExecutor)applicationContext.getBean("threadPool")).execute((Runnable) bean);
                } catch (MQClientException e) {
                    e.printStackTrace();
                }
            }
            if (bean instanceof PushConsumerJob){
                try {
                    ((PushConsumerJob) bean).getConsumer().start();
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
