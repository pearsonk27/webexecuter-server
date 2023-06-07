package com.webexecuter.server.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.Scheduler;

import com.webexecuter.server.entity.Script;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceUnitTests {

    @Mock
    private Scheduler taskScheduler;

    private SchedulerService schedulerService;

    @Test
    void testSchedule() throws Exception {
        schedulerService = new SchedulerService(taskScheduler);
        Script script = new Script();
        script.setCronExpression("0 0 12 * * ?");
        script.setStartDate(new Date());
        schedulerService.schedule(script);
        verify(taskScheduler).scheduleJob(any(), any());
    }
}
