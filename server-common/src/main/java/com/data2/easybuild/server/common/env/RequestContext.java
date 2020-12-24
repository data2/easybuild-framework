package com.data2.easybuild.server.common.env;

import java.util.HashMap;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午10:08
 */

public class RequestContext {
    private static ThreadLocal<HashMap<String,String>> threadLocal = new ThreadLocal<>();
    public static void addHeaders(HashMap<String, String> param) {
        threadLocal.set(param);
    }


    public static void rmHeaders() {
        threadLocal.remove();
    }
}
