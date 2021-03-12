package com.data2.easybuild.server.common.interceptor;

import com.data2.easybuild.server.common.env.RequestContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午9:36
 */
public class HeaderDealInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Enumeration<String> headerKeys = request.getHeaderNames();
        HashMap<String, String> param = new HashMap<>();
        while (headerKeys.hasMoreElements()){
            String key = headerKeys.nextElement();
            param.put(key,request.getHeader(key));
        }
        RequestContext.addHeaders(param);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestContext.rmHeaders();
    }
}
