package com.data2.easybuild.server.common.env;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.data2.easybuild.server.common.consts.ContextConst.CONTEXT;

/**
 * @author data2
 * @description
 * @date 2020/11/28 下午3:37
 */
@Configuration
@Data
public class SpringContextConfiguration {
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.profiles.active}")
    private String profileActive;

}
