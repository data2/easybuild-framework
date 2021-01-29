package com.data2.easybuild.server.common.dup;

import lombok.Data;

/**
 * @author data2
 * @description
 * @date 2021/1/28 下午9:21
 */
public enum  DupEnum {

    FRONT_ID("前端","FrontID"),
    REQUEST_HASH("请求体哈希","RequestHash");

    private String code;
    private String val;

    DupEnum(String code, String val){
        this.code = code;
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
