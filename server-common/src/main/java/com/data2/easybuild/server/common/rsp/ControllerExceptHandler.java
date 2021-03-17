package com.data2.easybuild.server.common.rsp;

import com.data2.easybuild.api.common.exception.EasyBusinessException;
import com.data2.easybuild.api.common.rest.dto.RestResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author data2
 * @description
 * @date 2021/3/16 下午3:33
 */
@ControllerAdvice
public class ControllerExceptHandler {

    @ExceptionHandler({Exception.class})
    public RestResponse exception(){
        return RestResponse.fail("9999","系统异常");
    }

    @ExceptionHandler({EasyBusinessException.class})
    public RestResponse exception(Exception e){
        return RestResponse.fail("9998","业务异常:" + e.getMessage());
    }

}
