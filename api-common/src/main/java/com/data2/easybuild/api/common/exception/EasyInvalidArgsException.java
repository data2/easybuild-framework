package com.data2.easybuild.api.common.exception;

/**
 * @author data2
 * @description
 * @date 2020/11/29 下午9:15
 */
public class EasyInvalidArgsException extends RuntimeException {
    /**
     * 无参构造函数
     */
    public EasyInvalidArgsException() {
        super();
    }

    /**
     * 标识异常描述信息的构造函数
     * @param message
     */
    public EasyInvalidArgsException(String message) {
        super(message);
    }

    /**
     * 包含异常描述信息和异常堆栈的构造函数
     * @param message
     * @param cause
     */
    public EasyInvalidArgsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 包含异常堆栈的构造函数
     * @param cause
     */
    public EasyInvalidArgsException(Throwable cause) {
        super(cause);
    }

}
