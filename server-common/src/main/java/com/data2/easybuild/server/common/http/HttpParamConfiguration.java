package com.data2.easybuild.server.common.http;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "easy.http")
public class HttpParamConfiguration {
    private Integer connectionRequestTimeout;
    private Integer connectTimeout;
    private Integer socketTimeout;
    private Integer maxTotal;
    private Integer defaultMaxPerRoute;
}
