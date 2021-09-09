package com.data2.easybuild.server.common.lock;

import com.data2.easybuild.api.common.exception.EasyBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午10:10
 */
@Slf4j
@Component
//@ConditionalOnBean(RedissonClient.class)
@ConditionalOnProperty(prefix = "easy.dup", name = "open", havingValue = "true")
public class RequestDupIntecept implements InitializingBean {
    public static final String NAME = "requestDupIntecept";
    @Autowired
    private RedissonClient redissonClient;

    public void intercept(String key, String expireTimeSecond) throws Exception{
        RTransaction trans = null;
        try {
            trans = redissonClient.createTransaction(TransactionOptions.defaults());
            RBucket<Integer> bucket = trans.getBucket(key);
            if (bucket.isExists()) {
                throw new EasyBusinessException("重复请求");
            }
            bucket.set(1, Long.parseLong(expireTimeSecond), TimeUnit.MILLISECONDS);
            trans.commit();
        } catch (Exception e) {
            if (!(e instanceof EasyBusinessException)) {
                trans.rollback();
            }else{
                throw e;
            }
        } finally {
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
