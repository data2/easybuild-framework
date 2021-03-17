package com.data2.easybuild.server.common.service;

import com.data2.easybuild.server.common.service.LoginService;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author data2
 * @description
 * @date 2021/3/17 上午9:43
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "easy.login")
public class LoginConfiguration {

    private boolean open = false;
    private List<String> pathPatterns;
    private List<String> excludePathPatterns;

}
