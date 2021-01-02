package com.weavemeng.www.springbootquartz.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: gaojian
 * E-mail: gaoj0203@163.com
 * Date: 2021/1/2
 * Time: 14:48
 * @author gaoji
 */
@Component
@Slf4j
public class RedisLock {

    /**
     * 锁过期时间
     */
    protected long internalLockLeaseTime = 30;
    /**
     * 获取锁的超时时间
     */
    private long timeout = 20000;

    private static final Long SUCCESS = 1L;

    /**
     *
     * 释放锁lua脚本
     */
    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 加锁
     * @param lockKey
     * @param id
     * @return
     */
    public boolean lock(String lockKey,String id){
        try {
            Thread.sleep((long)(1+Math.random()*(1000-1+1)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long start = System.currentTimeMillis();
        try{
            for(;;){
                //SET命令返回OK ，则证明获取锁成功
                Boolean ret = redisTemplate.opsForValue().setIfAbsent(lockKey, id, internalLockLeaseTime, TimeUnit.SECONDS);
                if(ret){
                    return true;
                }
                //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
                long l = System.currentTimeMillis() - start;
                if (l>=timeout) {
                    return false;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
        }
    }

    /**
     * 解锁
     * @param lockKey
     * @param id
     * @return
     */
    public boolean unlock(String lockKey,String id){
        //指定ReturnType为Long.class
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), id);
        return Objects.equals(result, SUCCESS);
    }
}