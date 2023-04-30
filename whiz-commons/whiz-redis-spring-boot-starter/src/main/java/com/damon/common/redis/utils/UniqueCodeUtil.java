package com.damon.common.redis.utils;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @description: 业务唯一code生成工具
 *
 * @author: damon.liu
 * @createDate: 2021/3/23
 */
@Component
public class UniqueCodeUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public String generateCode(String key, Long expireTime, String format){
        if (expireTime == null || expireTime == 0L) {
            expireTime = 24L;
        }
        long index = this.incr(key, expireTime);
        String indexKey = index + "";
        if (index < 100000) {
            indexKey = String.format(format, index);
        }
        return DateUtil.format(new Date(), "yyyyMMdd" + indexKey);
    }

    public String generateCode(String key, Long expireTime){
        if (expireTime == null || expireTime == 0L) {
            expireTime = 24L;
        }
        long index = this.incr(key, expireTime);
        String indexKey = index + "";
        if (index < 10000) {
            indexKey = String.format("%04d", index);
        }
        return DateUtil.format(new Date(), "yyyyMMdd" + indexKey);
    }

    /**
     * 自增长 利用redis自增长原子性
     * 定义为24 可以用作零点重置的策略
     *
     * @param key        key
     * @param expireTime 存活小时数
     * @return
     */
    public Long incr(String key, Long expireTime) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        long increment = entityIdCounter.getAndIncrement();
        //初始设置过期时间
        if (increment == 0) {
            entityIdCounter.expire(expireTime, TimeUnit.HOURS);
        }
        return increment;
    }
}
