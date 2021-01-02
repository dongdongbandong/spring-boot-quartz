package com.weavemeng.www.springbootquartz.controller;


import com.weavemeng.www.springbootquartz.dto.QuartzJobDTO;
import com.weavemeng.www.springbootquartz.enums.QuartzJobOperateEnum;
import com.weavemeng.www.springbootquartz.service.QuartzManagerService;
import io.swagger.annotations.Api;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: gaojian
 * E-mail: gaoj0203@163.com
 * Date: 2021/1/2
 * Time: 12:32
 */
@RestController
@Api(tags = "任务管理")
@RequestMapping(value = "quartz", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuartzJobController {

    @Autowired(required = false)
    private QuartzManagerService service;

    @PostMapping
    //@ApiOperation(value = "新增任务")
    public void addJob(@RequestBody QuartzJobDTO job) throws ClassNotFoundException, SchedulerException {
        checkService();
        service.addJob(job);
    }

    private void checkService() {
        if (service == null) {
            throw new RuntimeException("当前服务没有开启任务管理模式");
        }
    }

    @PutMapping("{id}")
    //@ApiOperation(value = "修改任务", notes = "operateEnum值：stop, resume, update")
    public void modifyJob(@PathVariable Integer id, @RequestBody QuartzJobDTO job, QuartzJobOperateEnum operateEnum) throws SchedulerException {
        checkService();
        service.modifyJob(id, operateEnum, job);
    }

    @DeleteMapping("{id}")
    //@ApiOperation(value = "删除任务")
    public void removeJob(@PathVariable Integer id) throws SchedulerException {
        checkService();
        service.removeJob(id);
    }
}