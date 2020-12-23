package com.data2.easybuild.server.common.env;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author data2
 * @description
 * @date 2020/12/23 上午10:01
 */
@NoArgsConstructor
@Data
public class ServerLog {
    private String uuid;
    private Date date;
    private String clientIp;
    private String clientType;
    private String serverIp;
    private String serverName;
    private String requestClass;
    private String request;
    private String result;
    private String errMsg;
    private String response;
}
