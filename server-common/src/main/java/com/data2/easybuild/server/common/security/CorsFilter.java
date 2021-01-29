package com.data2.easybuild.server.common.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author data2
 * @description
 * @date 2021/1/29 下午8:00
 * <p>
 * all interface enable cors , use this
 * if u want some interface, can use springboot anno @CrossOrigin
 */
@Configuration
@ConditionalOnProperty(prefix = "easy.cors", name = "enable", havingValue = "true")
public class CorsFilter implements Filter {

    private String allowOrigin;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        if (allowOrigin.contains(((HttpServletRequest) request).getHeader("Origin"))) {
            res.addHeader("Access-Control-Allow-Credentials", "true");
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
            res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
            if (((HttpServletRequest) request).getMethod() == "OPTIONS") {
                res.getOutputStream().print("ok");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
