package com.data2.easybuild.message.queue.common.rocketmq;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.annotation.*;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午3:29
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KafkaConsumer {
     String bootstrapServers();
     String topic();
     String groupId();
     String keyDeserializer() default "";
     String valueDeserializer() default "";
}
