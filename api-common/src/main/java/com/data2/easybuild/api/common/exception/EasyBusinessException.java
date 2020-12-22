package com.data2.easybuild.api.common.exception;

/**
 * @author data2
 * @description
 * @date 2020/11/29 下午9:15
 */
public class EasyBusinessException extends RuntimeException {
    /**
     * 无参构造函数
     */
    public EasyBusinessException() {
        super();
    }

    /**
     * 标识异常描述信息的构造函数
     * @param message
     */
    public EasyBusinessException(String message) {
        super(message);
    }

    /**
     * 包含异常描述信息和异常堆栈的构造函数
     * @param message
     * @param cause
     */
    public EasyBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 包含异常堆栈的构造函数
     * @param cause
     */
    public EasyBusinessException(Throwable cause) {
        super(cause);
    }

}
