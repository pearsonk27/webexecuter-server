package com.webexecuter.server.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.webexecuter.server.entity.Script;

@Service
public class ScriptExecutionService {

    private ScriptService scriptService;

    public ScriptExecutionService(ScriptService scriptService) {
        this.scriptService = scriptService;
    }
    
    public void execute(Long scriptId) {
        Script script = scriptService.findById(scriptId);
        String command = "python3 " + script.getLocation();
        try {
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Failed to execute script " + scriptId);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to execute script " + scriptId, e);
        }
    }

}
