package com.data2.easybuild.server.common.env;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.PrintStream;

/**
 * @author data2
 * @description
 * @date 2020/11/28 下午10:47
 */
@Component
@Slf4j
@Order(0)
public class EasyBuildBanner implements Banner {
    public static final String banner = "" +
            "  ______                         ____            _   _       _ \n" +
            " |  ____|                       |  _ \\          (_) | |     | |\n" +
            " | |__      __ _   ___   _   _  | |_) |  _   _   _  | |   __| |\n" +
            " |  __|    / _` | / __| | | | | |  _ <  | | | | | | | |  / _` |\n" +
            " | |____  | (_| | \\__ \\ | |_| | | |_) | | |_| | | | | | | (_| |\n" +
            " |______|  \\__,_| |___/  \\__, | |____/   \\__,_| |_| |_|  \\__,_|\n" +
            "                          __/ |                                \n" +
            "                         |___/                                 ";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        log.info(banner);
    }


}
