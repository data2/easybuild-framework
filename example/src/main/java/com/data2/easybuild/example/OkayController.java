package com.data2.easybuild.example;

import com.data2.easybuild.example.bean.TestReq;
import com.data2.easybuild.server.common.anno.DisableDuplicateSubmit;
import com.data2.easybuild.server.common.dup.DupEnum;
import com.data2.easybuild.server.common.security.EncryptRequest;
import org.springframework.web.bind.annotation.*;

/**
 * @author data2
 * @description
 * @date 2020/12/24 下午4:32
 */
@RestController
public class OkayController {

//    @DisableDuplicateSubmit(type = DupEnum.REQUEST_HASH, timeout = 2000)
    @PostMapping("/test")
//    @EncryptRequest
    public String test(@RequestBody TestReq testReq){
        System.out.println("comein ");
        return "test okay.";
    }
}
