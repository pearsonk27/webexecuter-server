package com.webexecuter.server.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webexecuter.server.entity.Script;
import com.webexecuter.server.service.SchedulerService;
import com.webexecuter.server.service.ScriptService;

@RestController
@RequestMapping("/scripts/{scriptId}/schedule")
public class ScheduleScriptEndpoint {
    private final ScriptService scriptService;
    private final SchedulerService schedulerService;

    public ScheduleScriptEndpoint(ScriptService scriptService, SchedulerService schedulerService) {
        this.scriptService = scriptService;
        this.schedulerService = schedulerService;
    }

    @PostMapping
    public void scheduleScript(@PathVariable Long scriptId) {
        Script script = scriptService.findById(scriptId);
        schedulerService.schedule(script);
    }
}
