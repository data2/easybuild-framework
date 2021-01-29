package com.data2.easybuild.server.common.dup;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author data2
 * @description
 * @date 2021/1/28 下午9:04
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface DisableDuplicateSubmit {
    String type() default "FrontID";
    String timeout() default "3";
}
