package com.data2.easybuild.server.common.anno;

import com.data2.easybuild.server.common.dup.DupEnum;

import java.lang.annotation.*;

import static com.data2.easybuild.server.common.dup.DupEnum.REQUEST_HASH;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author data2
 * @description
 * @date 2021/1/28 下午9:04
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DisableDuplicateSubmit {
    DupEnum type() default REQUEST_HASH;
    int timeout() default 2000;
}
