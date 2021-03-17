package com.data2.easybuild.server.common.interceptor;

import com.data2.easybuild.server.common.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author data2
 * @description
 * @date 2021/3/16 下午4:44
 */
public class LoginInterceptor implements HandlerInterceptor {
    private LoginService loginService;
    LoginInterceptor(LoginService loginService){
        this.loginService = loginService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return loginService.login();
    }
}
