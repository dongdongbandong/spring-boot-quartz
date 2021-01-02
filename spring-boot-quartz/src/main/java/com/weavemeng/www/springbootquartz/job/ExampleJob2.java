package com.weavemeng.www.springbootquartz.job;

import com.weavemeng.www.springbootquartz.redis.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Package: com.milla.study.netbase.expert.quartz.job
 * @Description: <定时任务>
 * @Author: MILLA
 * @CreateDate: 2020/5/12 18:58
 * @UpdateUser: MILLA
 * @UpdateDate: 2020/5/12 18:58
 * @UpdateRemark: <>
 * @Version: 1.0
 */
@Slf4j
public class ExampleJob2 implements Job {
    /**
     * 锁键
     */
    private String lock_key = "ExampleJob";

    @Resource
    RedisLock redisLock;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String id = UUID.randomUUID().toString();
        try {
            if(redisLock.lock(lock_key,id)){
                /*
                执行定时器方法
                 */
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("\"今天是 \" + \"yyyy 年 MM 月 dd 日 E HH 点 mm 分 ss 秒\"");
                log.info("ExampleJob2:{}",simpleDateFormat.format(new Date()));
                try {
                    Thread.sleep((long)(1+Math.random()*(10-1+1)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            redisLock.unlock(lock_key,id);
        }
    }
}