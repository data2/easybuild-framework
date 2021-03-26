package com.data2.easybuild.api.common.utils;

import com.data2.easybuild.api.common.dto.PageParam;
import com.data2.easybuild.api.common.exception.EasyInvalidArgsException;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2020/11/29 下午9:16
 */
public class ParamUtil {

    public static <T> void nullThrowException(T object, String message) {
        if (Objects.isNull(object)){
            throw new EasyInvalidArgsException(message);
        }
    }

    public static <T> T nullReturnDefaultVal(T object, T defaultVal) {
        if (Objects.isNull(object)){
            return defaultVal;
        }
        return object;
    }

    public static <T> T returnNotNull(T... objects) {
        for (T object: objects){
            if (Objects.nonNull(object)){
                return object;
            }
        }
        return null;
    }
}
