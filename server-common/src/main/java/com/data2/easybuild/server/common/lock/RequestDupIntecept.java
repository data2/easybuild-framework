package com.data2.easybuild.server.common.lock;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author data2
 * @description
 * @date 2020/11/27 下午10:10
 */
@Slf4j
@Component
@ConditionalOnBean(JedisPool.class)
public class RequestDupIntecept {
    @Autowired
    private JedisPool jedisPool;

    public boolean intercept(String key, String expireTimeSecond) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String script =
                    "if redis.call('incr',KEYS[1]) >= 2 then" +
                            "    return 1 " +
                            "else" +
                            "    redis.call('expire',KEYS[1], ARGV[1])" +
                            "    return 0 " +
                            "end";

            Object result = jedis.eval(script, Lists.newArrayList(key), Lists.newArrayList(expireTimeSecond));
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
