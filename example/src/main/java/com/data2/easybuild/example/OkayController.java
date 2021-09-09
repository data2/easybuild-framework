package com.data2.easybuild.example;

import com.data2.easybuild.api.common.exception.EasyBusinessException;
import com.data2.easybuild.example.bean.OrderBean;
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
@RequestMapping("/okay")
@RestController
public class OkayController {

    @Autowired(required = false)
    private SnowflakeIdWorker snowflakeIdWorker;


    @GetMapping("/responseWrapper")
    public Object ResponseWrapper(){
        return snowflakeIdWorker.nextId();
    }

    @GetMapping("/void")
    public Object void1(){
        return true;
    }

    @GetMapping("/except")
    public Object except(){
        throw new EasyBusinessException("error");
    }


    @PostMapping("/testEncrypt")
    @EncryptRequest
    @ResponseBody
    public Object testEncrypt(@RequestBody OrderBean orderBean){
        System.out.println(orderBean);
        return orderBean.getOrderId();
    }

    @PostMapping("/testDup")
    @DisableDuplicateSubmit(type = DupEnum.REQUEST_HASH, timeout = 2000)
    @ResponseBody
    public Object testDup(@RequestBody OrderBean orderBean){
        return orderBean.getOrderId();
    }
}
