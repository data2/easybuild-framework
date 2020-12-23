package com.data2.easybuild.server.common.aop;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午9:30
 */

import com.data2.easybuild.api.common.dto.AbstractRequest;
import com.data2.easybuild.api.common.exception.EasyBusinessException;
import com.data2.easybuild.api.common.rest.dto.AbstractRestRequest;
import com.data2.easybuild.api.common.rest.dto.RestResponse;
import com.data2.easybuild.server.common.env.ServerLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Date;

/**
 * 开放接口（RPC或REST API） 服务端统一处理切面
 * - 入参校验：接口请求入参校验
 * - 流控校验：服务器流控校验
 * - 优雅停机：接口处理前计数器加1，处理后减1
 * - 数据加锁：RedisLock
 * - 日志记录：成功或失败
 * - 异常转换：Throwable -> GiantException
 */
@Slf4j
public abstract class AbstractOpenApiAop {

    public Object doApi(ProceedingJoinPoint proceedingJoinPoint){
        Date start = new Date();
        AbstractRequest request = (AbstractRequest) proceedingJoinPoint.getArgs()[0];
        try {
            request.check();
            Object response = proceedingJoinPoint.proceed();
            okLog(start,request,response);
            return response;
        }catch (Throwable throwable) {
            EasyBusinessException exception = tranformException(throwable);
            failLog(start, request, exception);
            if (request instanceof AbstractRestRequest){
                return RestResponse.fail("1", exception.getMessage());
            }
            return null;
        } finally {
        }
    }

    private EasyBusinessException tranformException(Throwable throwable) {
        if (throwable instanceof EasyBusinessException){
            return (EasyBusinessException)throwable;
        }
        if (throwable instanceof NullPointerException){
            return EasyBusinessException.build("空指针异常");
        }else if (throwable instanceof IllegalArgumentException){
            return EasyBusinessException.build("不合法参数异常");
        }else if (throwable instanceof ArrayIndexOutOfBoundsException){
            return EasyBusinessException.build("数组越界异常");
        }else {
            return EasyBusinessException.build("异常");
        }
    }

    protected void failLog(Date start, AbstractRequest request, Throwable throwable){
        ServerLog serverLog = new ServerLog();
        log.info("失败日志:{}", serverLog);
    }

    private void okLog(Date start, AbstractRequest request, Object response){
        ServerLog serverLog = new ServerLog();
        log.info("成功日志:{}", serverLog);
    }

}
