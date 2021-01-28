package com.data2.easybuild.server.common.http;

import com.data2.easybuild.api.common.utils.ParamUtil;
import lombok.Data;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author data2
 * @description
 * @date 2021/1/28 上午11:25
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "easy.http")
@Data
public class EasyRestTemplate extends RestTemplate {

    private Integer connectionRequestTimeout;
    private Integer connectTimeout;
    private Integer socketTimeout;

    private Integer maxTotal;
    private Integer defaultMaxPerRoute;

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
        poolingHttpClientConnectionManager.setMaxTotal(ParamUtil.isNull(connectTimeout, 100));
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(ParamUtil.isNull(connectTimeout, 100));
        return poolingHttpClientConnectionManager;
    }

    @Bean
    public RequestConfig requestConfig() {
        return RequestConfig.custom().setConnectionRequestTimeout(ParamUtil.isNull(connectionRequestTimeout, 30 * 1000))
                .setConnectTimeout(ParamUtil.isNull(connectTimeout, 60 * 1000))
                .setSocketTimeout(ParamUtil.isNull(socketTimeout, 60 * 1000)).build();
    }

    @Bean
    public HttpClientBuilder httpClientBuilder() {
        return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager()).setDefaultRequestConfig(requestConfig());
    }


}
