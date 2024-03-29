package com.data2.easybuild.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Properties;

/**
 * @author data2
 * @description
 * @date 2020/11/29 上午12:35
 */
@Slf4j
public class PropertiesUtil {
    public static void print(Object properties) {
        try {

            if (properties instanceof Properties) {
                for (Object key : ((Properties) properties).keySet()) {
                    log.info("{}:{}", key, ((Properties) properties).get(key));
                }
            } else if (properties instanceof Map) {
                for (Object key : ((Map) properties).keySet()) {
                    log.info("{}:{}", key, ((Map) properties).get(key));
                }
            }
        } catch (Exception e) {
            log.info("print env param exception!");
        }
    }
}
