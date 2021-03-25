package com.data2.easybuild.server.common.security;

import java.lang.annotation.*;

/**
 * @author data2
 * @description
 * @date 2021/3/15 下午4:40
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface EncryptRequest {
}
