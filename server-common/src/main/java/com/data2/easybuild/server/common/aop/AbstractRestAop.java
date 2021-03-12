package com.data2.easybuild.server.common.aop;

import com.data2.easybuild.api.common.dto.Input;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午9:30
 */

public abstract class AbstractRestAop extends AbstractOpenApiAop {

    public Object doRestApi(ProceedingJoinPoint proceedingJoinPoint){
        Object[] args = proceedingJoinPoint.getArgs();
        if (!Objects.isNull(args) && args.length > 0){
            for(Object arg : args){
                if (arg instanceof Input){
                    this.writeParam((Input)arg);
                }
            }
        }
        return super.doApi(proceedingJoinPoint);
    }

    // 预留功能
    private void writeParam(Input arg) {
    }

}
