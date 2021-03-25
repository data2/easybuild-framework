package com.data2.easybuild.example;

import com.data2.easybuild.message.queue.common.rocketmq.RocketMqProducerConfig;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan({"com.data2.easybuild.example", "com.data2.easybuild"})
@EnableEncryptableProperties
public class ExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ExampleApplication.class, args);

        System.out.println(context.getBean(RocketMqProducerConfig.class).getNameSrvAddr());
    }


}
