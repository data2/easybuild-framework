package com.data2.easybuild.server.common.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@ConditionalOnBean(value = HttpParamConfiguration.class)
public class EasyHttp {
    @Autowired
    private HttpParamConfiguration httpParamConfiguration;

    @Autowired
    private CloseableHttpClient httpClient;

    @ToString
    static class ServiceResponse<T> {
        private String code;
        private String msg;
        private T data;
    }

    static class EasyHttpException extends RuntimeException {
        private int code;
        private String msg;

        public EasyHttpException(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 发起POST请求并处理响应
     *
     * @param url           请求的URL 【必传】
     * @param r             请求体内容
     * @param MAX_RETRIES   最大重试次数
     * @param logPreStr     日志前缀字符串
     * @param requestConfig 请求配置
     * @param <T>           返回值类型
     * @param <R>           请求体类型
     * @return ServiceResponse实例，包含响应内容
     */
    public <T, R> ServiceResponse<T> post(String url, R r, Integer MAX_RETRIES, String logPreStr, RequestConfig requestConfig) {
        if (StringUtils.isEmpty(url)) {
            throw new EasyHttpException(400, "url不能为空");
        }

        log.info("{}, 请求参数：", logPreStr);
        ServiceResponse<T> dataResponse = null;

        // counter
        if (Objects.isNull(MAX_RETRIES)) {
            MAX_RETRIES = 0;
        }
        int retryCount = 0;

        // 响应
        CloseableHttpResponse response = null;
        // 创建HttpPost实例，指定请求的URL
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头（如果需要的话）
        httpPost.setHeader("Content-Type", "application/json");

        if (Objects.isNull(requestConfig)) {
            requestConfig = RequestConfig.custom()
                    .setSocketTimeout(300000) // 设置读取超时时间，5min
                    .setConnectTimeout(60000) // 设置连接超时时间，1min
                    .setConnectionRequestTimeout(3000)
                    .build();
        }
        httpPost.setConfig(requestConfig);

        while (retryCount <= MAX_RETRIES) {
            try {
                // 设置请求体
                ObjectMapper objectMapper = new ObjectMapper();
                StringEntity entity = new StringEntity(objectMapper.writeValueAsString(r));
                httpPost.setEntity(entity);
                // 执行请求，获取响应
                response = httpClient.execute(httpPost);
                // 验证响应状态码
                if (response.getStatusLine().getStatusCode() == 200) {
                    // 读取响应实体内容
                    String responseBody = EntityUtils.toString(response.getEntity());
                    // 处理响应内容
                    dataResponse = objectMapper.readValue(responseBody, ServiceResponse.class);
                    log.info("{}请求接口成功,耗费{},响应{} ", logPreStr, (retryCount + 1) + "次调用 ", dataResponse.toString());
                    break; // 退出循环
                } else {
                    // 处理错误响应
                    log.info("{}请求接口失败: {}", logPreStr, response.getStatusLine().getStatusCode());
                }
            } catch (Exception e) {
                // 处理异常，例如网络错误、请求错误等
                log.error(org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
            } finally {
                // 确保响应被关闭
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        // 忽略关闭响应时的异常
                    }
                }

                // 增加重试计数器
                retryCount++;

                // 可以选择在这里添加延迟，以避免过快地重试
                if (retryCount <= MAX_RETRIES) {
                    try {
                        Thread.sleep(1000); // 等待1秒
                    } catch (Exception e2) {
                    }
                }
            }
        }
        // 使用连接池，不需要关闭链接资源

        if (retryCount == MAX_RETRIES) {
            log.info("{}请求接口连续超过{}次重试失败，请求参数：{}", logPreStr, MAX_RETRIES, r.toString());
        }
        return dataResponse;
    }

    @Bean
    public CloseableHttpClient httpClient() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(httpParamConfiguration.getMaxTotal()); // 最大连接数
        cm.setDefaultMaxPerRoute(httpParamConfiguration.getDefaultMaxPerRoute()); // 每个路由的最大连接数

        return HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

}
