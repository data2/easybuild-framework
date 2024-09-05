package com.data2.easybuild.server.common.http;

import com.data2.easybuild.api.common.utils.ParamUtil;
import lombok.Data;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author data2
 * @description use springboot restTemplate, by httpClient
 * @date 2021/1/28 上午11:25
 */
@Component
public class EasyRestTemplate extends RestTemplate {

    private HttpParamConfiguration httpParamConfiguration;

    @PostConstruct
    public void easyRestTemplate() {
        this.setRequestFactory(clientHttpRequestFactory());
    }

    /**
     * client connect strategy
     *
     * @return
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClientBuilder().build());
    }

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(ParamUtil.nullReturnDefaultVal(httpParamConfiguration.getConnectTimeout(), 100));
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(ParamUtil.nullReturnDefaultVal(httpParamConfiguration.getConnectTimeout(), 100));
        return poolingHttpClientConnectionManager;
    }

    @Bean
    public RequestConfig requestConfig() {
        return RequestConfig.custom().setConnectionRequestTimeout(ParamUtil.nullReturnDefaultVal(httpParamConfiguration.getConnectionRequestTimeout(), 30 * 1000))
                .setConnectTimeout(ParamUtil.nullReturnDefaultVal(httpParamConfiguration.getConnectTimeout(), 60 * 1000))
                .setSocketTimeout(ParamUtil.nullReturnDefaultVal(httpParamConfiguration.getSocketTimeout(), 60 * 1000)).build();
    }

    @Bean
    public HttpClientBuilder httpClientBuilder() {
        return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager()).setDefaultRequestConfig(requestConfig());
    }


}
