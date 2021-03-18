package com.data2.easybuild.server.common.interceptor;

import com.data2.easybuild.server.common.env.SpringContextHolder;
import com.data2.easybuild.server.common.service.LoginConfiguration;
import com.data2.easybuild.server.common.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2020/12/29 下午10:16
 */
@Slf4j
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired(required = false)
    private LoginService loginService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HeaderDealInterceptor()).addPathPatterns("/**");
        LoginConfiguration loginConfiguration = SpringContextHolder.getBean(LoginConfiguration.class);
        if (loginConfiguration.isOpen()) {
            if (Objects.isNull(loginService)){
                log.error("open login intercept, but not implement interface LoginService!");
                return;
            }
            registry.addInterceptor(new LoginInterceptor(loginService))
                    .addPathPatterns(loginConfiguration.getPathPatterns())
                    .excludePathPatterns(loginConfiguration.getExcludePathPatterns());
        }
    }
}
