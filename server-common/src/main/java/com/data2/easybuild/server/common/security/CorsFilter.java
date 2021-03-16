package com.data2.easybuild.server.common.security;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author data2
 * @description
 * @date 2021/1/29 下午8:00
 * <p>
 * all interface enable cors , use this
 * if u want some interface, can use springboot anno @CrossOrigin
 */
@Component
@Data
@ConfigurationProperties(prefix = "easy.cors")
@ConditionalOnProperty(prefix = "easy.cors", name = "enable", havingValue = "true")
public class CorsFilter implements Filter {

    private String allowOrigin;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        if (allowOrigin.contains(((HttpServletRequest) request).getHeader("host"))) {
            res.addHeader("Access-Control-Allow-Credentials", "true");
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
            res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
            if (((HttpServletRequest) request).getMethod() == "OPTIONS") {
                res.getOutputStream().print("ok");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            res.getOutputStream().print("cors request, forbidden");
        }
    }

    @Override
    public void destroy() {
    }
}
