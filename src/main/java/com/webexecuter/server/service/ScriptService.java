package com.webexecuter.server.service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.webexecuter.server.entity.Script;
import com.webexecuter.server.repository.ScriptRepository;

@Service
public class ScriptService {

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public Script save(Script script) {
        return scriptRepository.save(script);
    }

    public List<Script> findAll() {
        return scriptRepository.findAll();
    }

    public void deleteById(Long id) {
        scriptRepository.deleteById(id);
    }

    public Script findById(Long scriptId) {
        return null;
    }
}
