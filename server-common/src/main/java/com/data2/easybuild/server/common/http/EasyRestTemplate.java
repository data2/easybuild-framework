package com.data2.easybuild.server.common.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

/**
 * @author data2
 * @description
 * @date 2021/1/28 上午11:25
 */
@Component
public class EasyRestTemplate extends RestTemplate {

    public EasyRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory){
        super(clientHttpRequestFactory);
    }

}
