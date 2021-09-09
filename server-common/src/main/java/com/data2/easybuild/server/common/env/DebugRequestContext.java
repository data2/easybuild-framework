package com.data2.easybuild.server.common.env;

import com.data2.easybuild.api.common.exception.EasyBusinessException;
import com.data2.easybuild.utils.PropertiesUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.util.Map;
import java.util.Set;

import static com.data2.easybuild.server.common.consts.ContextConst.*;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午10:08
 */
@Component
@Slf4j
@Data
@ConditionalOnBean(DebugConfiguration.class)
public class DebugRequestContext extends RequestContext implements InitializingBean, ApplicationContextAware {

    private StandardServletEnvironment env;

    @Override
    public void afterPropertiesSet() {
        log.info("------------------------------------------------------------------------");
        log.info("-----------------------EasyBuild 启动参数-DEBUG--------------------------");
        log.info("------------------------------------------------------------------------");

        env.getPropertySources().stream().forEach(propertySource -> {
            if (SYSTEM_PROPERTIES.equals(propertySource.getName())) {
                log.info("--------------------------系统参数----------------------------------------");
            } else if (SYSTEM_ENVIRONMENT.equals(propertySource.getName())) {
                log.info("--------------------------系统环境参数-------------------------------------");
            } else if (propertySource.getName().startsWith(APPLICATION_CONFIG)) {
                log.info("--------------------------应用参数----------------------------------------");
                checkConfigRelation(propertySource.getSource());
            } else {
                return;
            }
            PropertiesUtil.print(propertySource.getSource());
        });
        log.info("------------------------------------------------------------------------");
    }

    private void checkConfigRelation(Object source) {
        if (source instanceof Map) {
            Set keySet = ((Map) source).keySet();
            if ((keySet.contains("easy.lock.open") && ((Map) source).get("easy.lock.open").toString().equals("true"))
                    || (keySet.contains("easy.dup.open") && ((Map) source).get("easy.dup.open").toString().equals("true"))) {
                if (StringUtils.isEmpty(((Map) source).get("easy.redis.host").toString()) || StringUtils.isEmpty(((Map) source).get("easy.redis.port").toString())) {
                    throw new EasyBusinessException("请先配置redis");
                }
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        env = (StandardServletEnvironment) applicationContext.getEnvironment();
    }
}
