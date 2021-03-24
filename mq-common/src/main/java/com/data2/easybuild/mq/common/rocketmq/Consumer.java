package com.data2.easybuild.mq.common.rocketmq;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午3:29
 */
@Target({ElementType.TYPE})
@Documented
public @interface Consumer {
    String consumerGroup();
    String topic();
    String tag() default "*" ;
    String namesrvAddr();
    String listener() default "";
}
