package com.data2.easybuild.server.common.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author data2
 * @description
 * @date 2021/1/28 上午11:25
 */
@Configuration
public class EasyRestTemplateConfig{

    @Bean
    public EasyRestTemplate easyRestTemplate(){
        return new EasyRestTemplate(clientHttpRequestFactory());
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(1000 * 30);
        factory.setReadTimeout(1000 * 30);
        return factory;
    }

}
