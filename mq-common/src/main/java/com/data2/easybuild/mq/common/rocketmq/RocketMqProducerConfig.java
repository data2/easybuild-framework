package com.data2.easybuild.mq.common.rocketmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午5:53
 */
@Data
@ConfigurationProperties(prefix = "easy.rocketmq.producer")
public class RocketMqProducerConfig {
    public String nameSrvAddr;
    public String group;
    public String topic;
    public String tag;
}


