package com.data2.easybuild.api.common.utils;

import com.data2.easybuild.api.common.dto.PageParam;
import com.data2.easybuild.api.common.exception.EasyInvalidArgsException;

/**
 * @author data2
 * @description
 * @date 2020/11/29 下午9:16
 */
public class ParamUtil {
    public static void isNull(PageParam pageParam, String message) {
        if (pageParam == null){
            throw new EasyInvalidArgsException(message);
        }
    }

    public static <T> T isNull(T object, T defaultVal) {
        if (object == null){
            return defaultVal;
        }
        return object;
    }
}
