package com.data2.easybuild.server.common.interceptor;

import com.data2.easybuild.server.common.env.SpringContextHolder;
import com.data2.easybuild.server.common.service.LoginConfiguration;
import com.data2.easybuild.server.common.service.LoginService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2020/12/29 下午10:16
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HeaderDealInterceptor()).addPathPatterns("/**");
        LoginConfiguration loginConfiguration = SpringContextHolder.getBean(LoginConfiguration.class);
        if (loginConfiguration.isOpen()) {
            registry.addInterceptor(new LoginInterceptor(SpringContextHolder.getBean(LoginService.class)))
                    .addPathPatterns(loginConfiguration.getPathPatterns())
                    .excludePathPatterns(loginConfiguration.getExcludePathPatterns());
        }
    }
}
