package com.data2.easybuild.example.aop;

import com.data2.easybuild.server.common.aop.AbstractRestAop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author data2
 * @description
 * @date 2020/12/24 下午4:18
 */
@Aspect
@Component
@Order(0)
public class ExampleControllerAop extends AbstractRestAop {

    @Pointcut("within(com.data2.easybuild.example..*Controller)")
    @Override
    public void apiPointCut(){
    }

    @Around(value = "apiPointCut()")
    @Override
    public Object doRestApi(ProceedingJoinPoint proceedingJoinPoint) {
        return super.doRestApi(proceedingJoinPoint);
    }
}
