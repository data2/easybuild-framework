package com.data2.easybuild.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author data2
 * @description
 * @date 2020/12/24 下午4:32
 */
@RestController
@RequestMapping("/path")
public class TestController {
    @GetMapping("/test")
    public String test(){
        return "test okay.";
    }
}
