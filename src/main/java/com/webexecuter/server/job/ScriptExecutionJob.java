package com.webexecuter.server.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.webexecuter.server.service.ScriptExecutionService;

public class ScriptExecutionJob implements Job {
    
    private final ScriptExecutionService scriptExecutionService;

    public ScriptExecutionJob(ScriptExecutionService scriptExecutionService) {
        this.scriptExecutionService = scriptExecutionService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Long scriptId = context.getJobDetail().getJobDataMap().getLong("scriptId");
        scriptExecutionService.execute(scriptId);
    }
}
