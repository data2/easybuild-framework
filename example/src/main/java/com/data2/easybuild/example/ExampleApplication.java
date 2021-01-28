package com.data2.easybuild.example;

import com.data2.easybuild.server.common.http.EasyRestTemplate;
import com.data2.easybuild.server.common.lock.SingleRedisDistributeLock;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan({"com.data2.easybuild.example", "com.data2.easybuild"})
public class ExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ExampleApplication.class, args);
        EasyRestTemplate rest = context.getBean(EasyRestTemplate.class);
        RequestConfig requestConfigRequestConfig = context.getBean(RequestConfig.class);
        PoolingHttpClientConnectionManager pool = context.getBean(PoolingHttpClientConnectionManager.class);
        SingleRedisDistributeLock lock = context.getBean(SingleRedisDistributeLock.class);
        System.out.println(rest.getConnectionRequestTimeout());
    }

    @GetMapping("/hi")
    public String hi() {
        return "easybuild-framework";
    }

}
