package com.data2.easybuild.message.queue.common.rocketmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author data2
 * @description
 * @date 2021/3/25 下午5:17
 */
@Configuration
public class ThreadPoolConfiguration {
    @Bean
    public ThreadPoolExecutor threadPool(){
        return new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
    }
}
