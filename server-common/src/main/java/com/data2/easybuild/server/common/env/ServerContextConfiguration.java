package com.data2.easybuild.server.common.env;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author data2
 * @description
 * @date 2020/11/28 下午3:37
 */
@Data
@Configuration
public class ServerContextConfiguration {

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;
}
