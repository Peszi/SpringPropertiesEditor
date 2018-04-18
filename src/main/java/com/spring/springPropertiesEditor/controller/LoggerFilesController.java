package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.LogsFileService;
import com.spring.springPropertiesEditor.service.LogsFileServiceImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/log")
public class LoggerFilesController {

    private LogsFileService logsFileService;

    public LoggerFilesController(LogsFileService logsFileService) {
        this.logsFileService = logsFileService;
    }

    @GetMapping("/audit")
    @ResponseBody
    FileSystemResource downloadAuditFile(HttpServletResponse response) throws FileNotFoundException {
        File file = this.logsFileService.getAuditLogFile();
        response.addHeader("Content-Disposition", "attachment; filename=" + LogsFileServiceImpl.AUDIT_FILE + LogsFileServiceImpl.FILE_LOG_EXT);
        response.setContentType("application/octet-stream");
        return new FileSystemResource(file);
    }

    @GetMapping("/application")
    @ResponseBody
    FileSystemResource downloadApplicationFile(HttpServletResponse response) throws FileNotFoundException {
        File file = this.logsFileService.getApplicationLogFile();
        response.addHeader("Content-Disposition", "attachment; filename=" + LogsFileServiceImpl.APPLICATION_FILE + LogsFileServiceImpl.FILE_LOG_EXT);
        response.setContentType("application/octet-stream");
        return new FileSystemResource(file);
    }
}
