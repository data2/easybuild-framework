package com.data2.easybuild.server.common.env;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static com.data2.easybuild.server.common.consts.ContextConst.CONTEXT;

/**
 * @author data2
 * @description
 * @date 2020/11/28 下午3:37
 */
@Configuration
@Data
@ConfigurationProperties(prefix = CONTEXT)
@ConditionalOnProperty(name = "debug", prefix = CONTEXT, havingValue = "true")
public class DebugConfiguration {
    private Boolean pretty;
    private Boolean debug;
}
