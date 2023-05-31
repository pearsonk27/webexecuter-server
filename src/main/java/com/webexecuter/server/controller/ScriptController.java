package com.webexecuter.server.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webexecuter.server.entity.Script;
import com.webexecuter.server.service.SchedulerService;
import com.webexecuter.server.service.ScriptExecutionService;
import com.webexecuter.server.service.ScriptService;

@RestController
@RequestMapping("/api/scripts")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private ScriptExecutionService scriptExecutionService;

    @GetMapping
    public List<Script> findAll() {
        return scriptService.findAll();
    }

    @PostMapping
    public Script save(@RequestBody Script script) throws SchedulerException {
        Script savedScript = scriptService.save(script);
        schedulerService.schedule(savedScript);
        return savedScript;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        scriptService.deleteById(id);
    }

    @PostMapping("/{scriptId}/execute")
    public ResponseEntity<Void> executeScript(@PathVariable Long scriptId) {
        scriptExecutionService.execute(scriptId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<Script> uploadScript(@RequestParam("file") MultipartFile file) throws IOException {
        // Save the file to the server
        String filename = file.getOriginalFilename();
        File tempFile = File.createTempFile("script-", "-" + filename);
        file.transferTo(tempFile);

        // Create a new Script object
        Script script = new Script();
        script.setName(filename);
        script.setLocation(tempFile.getAbsolutePath());

        // Save the script to the database
        scriptService.save(script);

        return ResponseEntity.ok(script);
    }

}
