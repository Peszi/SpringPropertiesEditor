package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.ChangeLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoggerController {

    private static final String FILE_FAIL_MESSAGE = "Changes cannot be empty!";

    private ChangeLogService changeLogService;

    public LoggerController(ChangeLogService changeLogService) {
        this.changeLogService = changeLogService;
    }

    @GetMapping("/changes/json")
    void downloadJsonChanges(HttpServletResponse response) {
        try {
            if (!this.changeLogService.isEmpty()) {
                response.addHeader("Content-Disposition", "attachment; filename=AuditLog.json");
                response.setContentType("application/octet-stream");
                this.changeLogService.getChangesJsonStream(response.getOutputStream());
                response.flushBuffer();
            } else {
                response.sendRedirect("/properties?message=" + LoggerController.FILE_FAIL_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/changes/yaml")
    void downloadYamlChanges(HttpServletResponse response) {
        try {
            if (!this.changeLogService.isEmpty()) {
                response.addHeader("Content-Disposition", "attachment; filename=AuditLog.yaml");
                response.setContentType("application/octet-stream");
                this.changeLogService.getChangesYamlStream(response.getOutputStream());
                response.flushBuffer();
            } else {
                response.sendRedirect("/properties?message=" + LoggerController.FILE_FAIL_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/spring")
    void downloadSpringLogs(HttpServletResponse response) {
        try {
            if (!this.changeLogService.isEmpty()) {
                response.addHeader("Content-Disposition", "attachment; filename=AuditLog.yaml");
                response.setContentType("application/octet-stream");
                this.changeLogService.getChangesYamlStream(response.getOutputStream());
                response.flushBuffer();
            } else {
                response.sendRedirect("/properties?message=" + LoggerController.FILE_FAIL_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
