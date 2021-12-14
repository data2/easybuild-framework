package com.data2.easybuild.message.queue.common.rocketmq;

import com.data2.easybuild.api.common.exception.EasyBusinessException;
import com.data2.easybuild.api.common.utils.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
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
public class LoadThread extends ConsumerLoader {

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
            check(ParamUtil.returnNotNull(annotation.consumerGroup(), rocketMqConsumerConfig.getGroup()));
            if (bean instanceof AbstractPullConsumerJob) {
                ((AbstractPullConsumerJob) bean).setConsumer(new DefaultMQPullConsumer(annotation.consumerGroup()));
                DefaultMQPullConsumer consumer = (DefaultMQPullConsumer) ((AbstractPullConsumerJob) bean).getConsumer();
                consumer.setNamesrvAddr(annotation.namesrvAddr());
            }
            if (bean instanceof AbstractPushConsumerJob) {
                ((AbstractPushConsumerJob) bean).setConsumer(new DefaultMQPushConsumer(annotation.consumerGroup()));
                DefaultMQPushConsumer consumer = (DefaultMQPushConsumer) ((AbstractPushConsumerJob) bean).getConsumer();
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
