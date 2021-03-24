package com.data2.easybuild.mq.common.rocketmq;

import com.data2.easybuild.api.common.exception.EasyBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午4:59
 */
@Slf4j
@Component
public class LoadThread implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> cls = bean.getClass();
        Consumer annotation = cls.getDeclaredAnnotation(Consumer.class);
        if (Objects.nonNull(annotation)) {
            if (StringUtils.isEmpty(annotation.topic())) {
                throw new EasyBusinessException("consumer topic must config, please check your configuration!");
            }
            if (StringUtils.isEmpty(annotation.listener())) {
                throw new EasyBusinessException("consumer listener must config, please check your configuration!");
            }
            if (StringUtils.isEmpty(annotation.namesrvAddr())) {
                throw new EasyBusinessException("consumer namesrvAddr must config, please check your configuration!");
            }
            if (bean instanceof PullConsumerJob) {
                ((PullConsumerJob) bean).setConsumer(new DefaultMQPullConsumer(annotation.consumerGroup()));
                DefaultMQPullConsumer consumer = (DefaultMQPullConsumer) ((PullConsumerJob) bean).getConsumer();
                consumer.setNamesrvAddr(annotation.namesrvAddr());
                try {
                    consumer.fetchSubscribeMessageQueues(annotation.topic());
                } catch (MQClientException e) {
                    e.printStackTrace();
                    throw new EasyBusinessException("PullConsumerJob subscribe exception occur");
                }
            }
            if (bean instanceof PushConsumerJob) {
                ((PushConsumerJob) bean).setConsumer(new DefaultMQPushConsumer(annotation.consumerGroup()));
                DefaultMQPushConsumer consumer = (DefaultMQPushConsumer) ((PushConsumerJob) bean).getConsumer();
                consumer.setNamesrvAddr(annotation.namesrvAddr());
                try {
                    consumer.subscribe(annotation.topic(), annotation.tag());
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
}
