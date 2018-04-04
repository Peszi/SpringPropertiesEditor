package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.model.Log;
import com.spring.springPropertiesEditor.respository.ChangeLogRepository;
import com.spring.springPropertiesEditor.service.ChangesManager;
import com.spring.springPropertiesEditor.service.PropertiesManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangesController {

    private ChangeLogRepository changeLogRepository;

    public ChangesController(ChangeLogRepository changeLogRepository) {
        this.changeLogRepository = changeLogRepository;
    }

    @RequestMapping("/changes")
    Iterable<Log> getChanges() {
        return this.changeLogRepository.findAll();
    }

}
