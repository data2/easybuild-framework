package com.data2.easybuild.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class IpUtils {

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("X-Real-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getServerIpAddr() {
        String address = "unknown";
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("get server local ip exception ", e);
        }
        return address;
    }
}
