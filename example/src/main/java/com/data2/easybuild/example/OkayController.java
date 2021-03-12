package com.data2.easybuild.example;

import com.data2.easybuild.server.common.anno.DisableDuplicateSubmit;
import com.data2.easybuild.server.common.dup.DupEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author data2
 * @description
 * @date 2020/12/24 下午4:32
 */
@RestController
public class OkayController {

//    @DisableDuplicateSubmit(type = DupEnum.REQUEST_HASH, timeout = 2000)
    @GetMapping("/test")
    public String test(){
        System.out.println("comein ");
        return "test okay.";
    }
}
