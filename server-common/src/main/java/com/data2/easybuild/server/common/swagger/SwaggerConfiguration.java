package com.data2.easybuild.server.common.swagger;

import com.data2.easybuild.server.common.env.ServerContextConfiguration;
import com.data2.easybuild.server.common.env.SpringContextConfiguration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Objects;

import static com.data2.easybuild.server.common.consts.ContextConst.DEV;
import static com.data2.easybuild.server.common.consts.ContextConst.TEST;
import static com.data2.easybuild.server.common.consts.SwaggerConst.PREFIX;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午10:18
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = PREFIX)
@Configuration
@Profile({"dev", "test"})
@EnableSwagger2
@ConditionalOnProperty(havingValue = "true", prefix = PREFIX, name = "enable")
public class SwaggerConfiguration implements WebMvcConfigurer, InitializingBean {

    @Autowired
    private SpringContextConfiguration configuration;
    @Autowired
    private ServerContextConfiguration server;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!Objects.isNull(configuration) && Arrays.asList(DEV,TEST).contains(configuration.getProfileActive().toLowerCase())) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");

            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }else{
            log.info("not dev or test, not suggest show swagger");
        }
    }

    private Boolean enable;
    private String profile;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("------------------------------------------------------------------------");
        log.info("-----------------------Swagger-Ui---------------------------------------");
        log.info("------------------------------------------------------------------------");
        log.info("Swagger-Ui地址:http://localhost:{}{}/swagger-ui.html", server.getPort(), server.getContextPath());
        log.info("------------------------------------------------------------------------");

    }
}
