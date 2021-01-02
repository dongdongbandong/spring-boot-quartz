package com.weavemeng.www.springbootquartz.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @Package: com.milla.study.netbase.expert.quartz.config
 * @Description: <动态任务配置类>
 * @Author: MILLA
 * @CreateDate: 2020/5/12 19:36
 * @UpdateUser: MILLA
 * @UpdateDate: 2020/5/12 19:36
 * @UpdateRemark: <>
 * @Version: 1.0
 */
@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("quartz") Properties properties) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        try {
            schedulerFactoryBean.setOverwriteExistingJobs(true);
            schedulerFactoryBean.setQuartzProperties(properties);
            schedulerFactoryBean.setJobFactory(new SpringBeanJobFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedulerFactoryBean;
    }

    /**
     * 指定quartz.properties，可在配置文件中配置相关属性
     * @return
     * @throws IOException
     */
    @Bean("quartz")
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * 创建schedule
      * @param scheduler
     * @return
     */
    @Bean(name = "scheduler")
    public Scheduler scheduler(SchedulerFactoryBean scheduler) {
        return scheduler.getScheduler();
    }


}