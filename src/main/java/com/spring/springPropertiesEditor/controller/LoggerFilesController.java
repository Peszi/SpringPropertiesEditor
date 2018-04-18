package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.LogFileServiceImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
@RequestMapping("/log")
public class LoggerFilesController {

    private static final String AUDIT_FILE_NOT_EXISTS = "Audit file not exists!";

    private LogFileServiceImpl logsFileService;

    public LoggerFilesController(LogFileServiceImpl logsFileService) {
        this.logsFileService = logsFileService;
    }

    @GetMapping("/audit")
    @ResponseBody
    FileSystemResource downloadAuditFile(HttpServletResponse response) {
        File file = this.logsFileService.getAuditLogFile();
        response.addHeader("Content-Disposition", "attachment; filename=" + LogFileServiceImpl.AUDIT_FILE + LogFileServiceImpl.FILE_LOG_EXT);
        response.setContentType("application/octet-stream");
        return new FileSystemResource(file);
    }

    @GetMapping("/application")
    @ResponseBody
    FileSystemResource downloadApplicationFile(HttpServletResponse response) {
        File file = this.logsFileService.getApplicationLogFile();
        response.addHeader("Content-Disposition", "attachment; filename=" + LogFileServiceImpl.APPLICATION_FILE + LogFileServiceImpl.FILE_LOG_EXT);
        response.setContentType("application/octet-stream");
        return new FileSystemResource(file);
    }
}
