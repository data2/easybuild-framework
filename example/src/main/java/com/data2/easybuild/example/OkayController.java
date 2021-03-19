package com.data2.easybuild.example;

import com.data2.easybuild.api.common.exception.EasyBusinessException;
import com.data2.easybuild.example.bean.TestReq;
import com.data2.easybuild.server.common.anno.DisableDuplicateSubmit;
import com.data2.easybuild.server.common.dup.DupEnum;
import com.data2.easybuild.server.common.security.EncryptRequest;
import com.data2.easybuild.server.common.seq.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @author data2
 * @description
 * @date 2020/12/24 下午4:32
 */
@RestController
public class OkayController {

    @Autowired(required = false)
    private SnowflakeIdWorker snowflakeIdWorker;

//    @DisableDuplicateSubmit(type = DupEnum.REQUEST_HASH, timeout = 2000)
    @GetMapping("/test")
//    @EncryptRequest
    public Object test(){
        System.out.println("comein ");
        System.out.println(snowflakeIdWorker.nextId());
        return snowflakeIdWorker.nextId();
        //throw new EasyBusinessException("837942");
    }
}
