package com.data2.easybuild.server.common.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午9:20
 */
@RestController
public class ServerController {
    @GetMapping("/health")
    public String check(){
        return "ok";
    }
}
