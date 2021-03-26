package com.data2.easybuild.message.queue.common.rocketmq;

import com.data2.easybuild.api.common.exception.EasyBusinessException;
import com.data2.easybuild.configuration.common.AbstractBeanFilter;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author data2
 * @description
 * @date 2021/3/26 下午3:16
 */
public abstract class ConsumerLoader extends AbstractBeanFilter {
    private CopyOnWriteArraySet<String> consumerGroup = new CopyOnWriteArraySet();

    protected synchronized void check(String groupName){
        if (consumerGroup.contains(groupName)){
            throw new EasyBusinessException("duplicate consumerGroupName, please check !");
        }else {
            consumerGroup.add(groupName);
        }
    }
}
