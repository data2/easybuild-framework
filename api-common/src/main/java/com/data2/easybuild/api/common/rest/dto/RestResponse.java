package com.data2.easybuild.api.common.rest.dto;

import com.data2.easybuild.api.common.dto.Output;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author data2
 * @description
 * @date 2020/11/29 下午9:20
 */
@Getter
@Setter
@NoArgsConstructor
public class RestResponse<T> implements Output {

    private boolean success;

    private T data;

    private String code;

    private String message;

    public static <T> RestResponse<T> ok(T data){
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(true);
        restResponse.setCode("0");
        restResponse.setMessage("success");
        restResponse.setData(data);
        return restResponse;
    }

    public static <T> RestResponse<T> fail(String code, String message){
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(false);
        restResponse.setCode(code);
        restResponse.setMessage(message);
        restResponse.setData(null);
        return restResponse;
    }

}
