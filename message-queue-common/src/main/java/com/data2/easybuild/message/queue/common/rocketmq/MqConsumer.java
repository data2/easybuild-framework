package com.data2.easybuild.message.queue.common.rocketmq;

import org.springframework.beans.factory.DisposableBean;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午3:26
 */
public interface MqConsumer extends DisposableBean , Runnable{
}
