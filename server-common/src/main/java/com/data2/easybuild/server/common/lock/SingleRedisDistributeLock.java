package com.data2.easybuild.server.common.lock;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午10:10
 */
@Slf4j
@Configuration
@ConditionalOnBean(JedisPool.class)
@ConditionalOnProperty(name = "open", prefix = "easy.lock", havingValue = "true")
public class SingleRedisDistributeLock {
    private long timeout;
    private long expireTime;
    @Autowired
    private JedisPool jedisPool;

    private SetParams setParams = SetParams.setParams().nx().px(expireTime);

    public boolean tryLock(String key, String val) {
        return tryLock(key, val, timeout);
    }

    public boolean tryLock(String key, String val, long timeoutParam) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long start = System.currentTimeMillis();
            long keyTimeOut = timeoutParam == 0 ? timeout : timeoutParam;
            while ((System.currentTimeMillis() - start) < keyTimeOut) {
                String ok = jedis.set(key, val, setParams);
                if ("OK".equals(ok)) {
                    return true;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public boolean unlock(String key, String  val){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String script =
                    "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                            "   return redis.call('del',KEYS[1]) " +
                            "else" +
                            "   return 0 " +
                            "end";

            Object result = jedis.eval(script, Lists.newArrayList(key), Lists.newArrayList(val));
            if ("1".equals(result.toString())) {
                return true;
            }
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

}
