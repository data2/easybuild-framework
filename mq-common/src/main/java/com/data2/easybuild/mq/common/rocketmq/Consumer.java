package com.data2.easybuild.mq.common.rocketmq;

import org.springframework.context.annotation.Scope;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author data2
 * @description
 * @date 2021/3/24 下午3:29
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Consumer {
    String consumerGroup();
    String topic();
    String tag() default "*" ;
    String namesrvAddr();
    Class listener();
}
