package com.data2.easybuild.server.common.env;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author data2
 * @description
 * @date 2020/12/23 上午10:01
 */
@NoArgsConstructor
@Getter
public class ServerLog {
    private String uuid;
    private long interfaceCostTime;
    private String clientIp;
    private String clientType;
    private String serverIp;
    private String serverName;
    private String requestClass;
    private String request;
    private String result;
    private String errMsg;
    private String response;

    public ServerLog setRequest(String request) {
        this.request = request;
        return this;
    }

    public ServerLog setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    public ServerLog setResponse(String response) {
        this.response = response;
        return this;
    }

    public ServerLog setRequestClass(String requestClass) {
        this.requestClass = requestClass;
        return this;
    }

    public ServerLog setInterfaceCostTime(long interfaceCostTime) {
        this.interfaceCostTime = interfaceCostTime;
        return this;
    }

    public ServerLog setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }


}
