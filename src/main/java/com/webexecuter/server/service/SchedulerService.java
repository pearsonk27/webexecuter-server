package com.webexecuter.server.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;
import org.quartz.Scheduler;

import com.webexecuter.server.entity.Script;
import com.webexecuter.server.job.ScriptExecutionJob;

@Service
public class SchedulerService {

    private final Scheduler taskScheduler;

    public SchedulerService(Scheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void schedule(Script script) {
        Trigger trigger = buildTrigger(script);
        JobDetail jobDetail = buildJobDetail(script);

        try {
            taskScheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to schedule script " + script.getId(), e);
        }
    }

    private JobDetail buildJobDetail(Script script) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("scriptId", script.getId());

        return JobBuilder.newJob(ScriptExecutionJob.class)
                .withIdentity("script_" + script.getId() + "_job")
                .withDescription("Execute script " + script.getId() + " (" + script.getName() + ")")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildTrigger(Script script) {
        return TriggerBuilder.newTrigger()
                .withIdentity("script_" + script.getId() + "_trigger")
                .withDescription("Trigger for script " + script
                        .getId())
                .withSchedule(CronScheduleBuilder.cronSchedule(script.getCronExpression()))
                .startAt(script.getStartDate())
                .build();
    }

}
