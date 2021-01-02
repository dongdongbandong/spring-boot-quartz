package com.weavemeng.www.springbootquartz.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: gaojian
 * E-mail: gaoj0203@163.com
 * Date: 2021/1/2
 * Time: 17:34
 * @author gaoji
 */
@Configuration
public class RedisConfig {

    @Resource
    private RedisTemplate redisTemplate;

    @Bean
    public RedisTemplate redisTemplateInit() {
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}