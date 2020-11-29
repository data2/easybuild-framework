package com.data2.easybuild.server.common.env;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午10:08
 */
@Component
@Slf4j
@ConditionalOnMissingBean(DebugConfiguration.class)
public class SimpleRequestContext extends RequestContext implements InitializingBean {

    @Autowired
    private SpringContextConfiguration springContextConfiguration;

    @Autowired
    private ServerContextConfiguration serverContextConfiguration;

    @Override
    public void afterPropertiesSet() {
        log.info("------------------------------------------------------------------------");
        log.info("-----------------------EasyBuild 启动参数--------------------------------");
        log.info("------------------------------------------------------------------------");

        log.info("应用名:{}",springContextConfiguration.getApplicationName());
        log.info("启动环境:{}", springContextConfiguration.getProfileActive());
        log.info("服务端口:{}", serverContextConfiguration.getPort());
        log.info("服务路径:{}", serverContextConfiguration.getContextPath());
        log.info("------------------------------------------------------------------------");

    }
}
