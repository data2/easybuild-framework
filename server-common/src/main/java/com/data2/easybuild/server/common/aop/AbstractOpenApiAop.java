package com.data2.easybuild.server.common.aop;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午9:30
 */

import com.alibaba.fastjson.JSON;
import com.data2.easybuild.api.common.dto.AbstractRequest;
import com.data2.easybuild.api.common.exception.EasyBusinessException;
import com.data2.easybuild.api.common.rest.dto.AbstractRestRequest;
import com.data2.easybuild.api.common.rest.dto.RestResponse;
import com.data2.easybuild.server.common.anno.DisableDuplicateSubmit;
import com.data2.easybuild.server.common.env.RequestContext;
import com.data2.easybuild.server.common.env.ServerLog;
import com.data2.easybuild.server.common.env.SpringContextHolder;
import com.data2.easybuild.server.common.lock.RequestDupIntecept;
import com.data2.easybuild.server.common.util.IpUtils;
import jodd.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Objects;

/**
 * 开放接口（RPC或REST API） 服务端统一处理切面
 * - 入参校验：接口请求入参校验
 * - 防重复提交
 * - 流控校验：服务器流控校验
 * - 优雅停机：接口处理前计数器加1，处理后减1
 * - 数据加锁：RedisLock
 * - 日志记录：成功或失败
 * - 异常转换：Throwable -> EasyBusinessException
 */
@Slf4j
public abstract class AbstractOpenApiAop {

    public abstract void apiPointCut();

    public Object doApi(ProceedingJoinPoint proceedingJoinPoint) {
        Date start = new Date();
        AbstractRequest request = null;
        try {
            Boolean gotFirstInput = false;
            Object[] args = proceedingJoinPoint.getArgs();
            if (!Objects.isNull(args) && args.length > 0) {
                for (Object arg : args) {
                    if (arg instanceof AbstractRequest) {
                        ((AbstractRequest) arg).check();
                        if (!gotFirstInput) {
                            gotFirstInput = true;
                            request = (AbstractRequest) arg;
                        }
                    }
                }
            }
            disableDup(proceedingJoinPoint, request);
            Object response = proceedingJoinPoint.proceed();
            okLog(start, request, response);
            return RestResponse.ok(response);
        } catch (Throwable throwable) {
            EasyBusinessException exception = tranformException(throwable);
            failLog(start, request, exception);
            if (request instanceof AbstractRestRequest) {
                return RestResponse.fail("1", exception.getMessage());
            }
            return RestResponse.fail("9999", "系统异常");
        } finally {
        }
    }

    protected void disableDup(ProceedingJoinPoint proceedingJoinPoint, AbstractRequest request) throws Exception {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        DisableDuplicateSubmit anno = signature.getMethod().getDeclaredAnnotation(DisableDuplicateSubmit.class);
        Class<?> cls = proceedingJoinPoint.getTarget().getClass();
        if (anno == null) {
            anno = cls.getDeclaredAnnotation(DisableDuplicateSubmit.class);
        }
        if (anno != null) {
            StringBuffer key = new StringBuffer(IpUtils.getIpAddr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()));
            switch (anno.type()) {
                case FRONT_ID:
                    key.append(StringUtils.isEmpty(RequestContext.getHeader("frontId")) ? request.getFrontID() : RequestContext.getHeader("frontId"));
                    break;
                case REQUEST_HASH:
                    key.append(String.valueOf(request.toString().hashCode()));
                    break;
            }
            RequestDupIntecept bean = SpringContextHolder.getBean(RequestDupIntecept.class, RequestDupIntecept.NAME);
            if (Objects.isNull(bean)){
                log.error("防重功能失败，请先开启该项配置，easy.dup.open=true，并且配置redis");
                return;
            }
            bean.intercept(key.toString(), String.valueOf(anno.timeout()));
        }

    }

    private EasyBusinessException tranformException(Throwable throwable) {
        if (throwable instanceof EasyBusinessException) {
            return (EasyBusinessException) throwable;
        }
        if (throwable instanceof NullPointerException) {
            return EasyBusinessException.build("空指针异常");
        } else if (throwable instanceof IllegalArgumentException) {
            return EasyBusinessException.build("不合法参数异常");
        } else if (throwable instanceof ArrayIndexOutOfBoundsException) {
            return EasyBusinessException.build("数组越界异常");
        } else {
            return EasyBusinessException.build("异常");
        }
    }

    private void failLog(Date start, AbstractRequest request, EasyBusinessException throwable) {
        log.info("失败日志:{}", JSON.toJSONString(new ServerLog()
                .setInterfaceCostTime((System.currentTimeMillis() - start.getTime()) / 1000)
                .setErrMsg(ExceptionUtil.exceptionChainToString(throwable))
                .setRequest(JSON.toJSONString(request))
                .setRequestClass(request != null ? request.getClass().getName(): null)
                .setUuid(request != null ? request.getUuid() : null)));
    }

    private void okLog(Date start, AbstractRequest request, Object response) {
        log.info("成功日志:{}", JSON.toJSONString(new ServerLog()
                .setInterfaceCostTime((System.currentTimeMillis() - start.getTime()) / 1000)
                .setRequest(JSON.toJSONString(request))
                .setRequestClass(request != null ? request.getClass().getName(): null)
                .setUuid(request != null ? request.getUuid() : null)
                .setResponse(JSON.toJSONString(response))));
    }

}
