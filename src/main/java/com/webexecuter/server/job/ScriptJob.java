package com.webexecuter.server.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.webexecuter.server.entity.Script;

public class ScriptJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Script script = (Script) context.getMergedJobDataMap().get("script");

        // execute the Python script using a command line interface or library like
        // PythonShell
        // update the script status in the database
    }
}
