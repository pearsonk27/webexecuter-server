package com.webexecuter.server.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webexecuter.server.entity.Script;
import com.webexecuter.server.repository.ScriptRepository;

@Service
public class ScriptService {

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private SchedulerService schedulerService;

    public Script save(Script script) {
        Script returnScript = scriptRepository.save(script);
        schedulerService.schedule(returnScript);
        return returnScript;
    }

    public List<Script> findAll() {
        return scriptRepository.findAll();
    }

    public void deleteById(Long id) {
        scriptRepository.deleteById(id);
    }

    public Script findById(Long scriptId) {
        Optional<Script> scriptOptional = scriptRepository.findById(scriptId);
        if (scriptOptional.isEmpty()) {
            throw new ObjectNotFoundException(scriptId, Script.class.getName());
        }
        return scriptOptional.get();
    }
}
