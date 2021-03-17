package com.data2.easybuild.example;

import com.data2.easybuild.server.common.service.LoginService;
import org.springframework.stereotype.Component;

/**
 * @author data2
 * @description
 * @date 2021/3/17 上午10:20
 */
@Component
public class LoginServiceImpl implements LoginService {
    public boolean login() {
        return true;
    }
}
