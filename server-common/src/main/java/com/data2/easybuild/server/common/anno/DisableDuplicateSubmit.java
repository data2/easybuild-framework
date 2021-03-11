package com.data2.easybuild.server.common.anno;

import com.data2.easybuild.server.common.dup.DupEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static com.data2.easybuild.server.common.dup.DupEnum.REQUEST_HASH;

/**
 * @author data2
 * @description
 * @date 2021/1/28 下午9:04
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface DisableDuplicateSubmit {
    DupEnum type() default REQUEST_HASH;
    int timeout() default 2000;
}
