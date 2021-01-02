package com.weavemeng.www.springbootquartz.service.impl;

import com.weavemeng.www.springbootquartz.dto.QuartzJobDTO;
import com.weavemeng.www.springbootquartz.enums.QuartzJobStateEnum;
import com.weavemeng.www.springbootquartz.mapper.QuartzJobDTOMapper;
import com.weavemeng.www.springbootquartz.service.QuartzJobService;
import com.weavemeng.www.springbootquartz.util.QuartzManager;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package: com.milla.study.netbase.expert.quartz.service.impl
 * @Description: <启动的时候加载加载在运行状态的任务并启动>
 * @Author: MILLA
 * @CreateDate: 2020/5/12 19:39
 * @UpdateUser: MILLA
 * @UpdateDate: 2020/5/12 19:39
 * @UpdateRemark: <>
 * @Version: 1.0
 */
@Service
public class ExampleQuartzJobOneInitServiceImpl implements QuartzJobService {

    @Resource
    private QuartzManager quartzUtil;
    @Resource
    private QuartzJobDTOMapper mapper;

    @Override
    public void initSchedule() throws SchedulerException, ClassNotFoundException {
        // 获取所有报告的定时任务
        List<QuartzJobDTO> jobList = mapper.selectJobByGroup("report");
        if (CollectionUtils.isEmpty(jobList)) {
            return;
        }
        for (QuartzJobDTO job : jobList) {
            if (QuartzJobStateEnum.RUNNING.getCode().equals(job.getJobStatus())) {
                quartzUtil.addJob(job);
            }
        }
    }
}