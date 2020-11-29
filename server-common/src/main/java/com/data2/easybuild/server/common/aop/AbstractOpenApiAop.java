package com.data2.easybuild.server.common.aop;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午9:30
 */

/**
 * 开放接口（RPC或REST API） 服务端统一处理切面
 * - 入参校验：接口请求入参校验
 * - 流控校验：服务器流控校验
 * - 优雅停机：接口处理前计数器加1，处理后减1
 * - 数据加锁：RedisLock
 * - 日志记录：成功或失败
 * - 异常转换：Throwable -> GiantException
 */

public abstract class AbstractOpenApiAop {
}
