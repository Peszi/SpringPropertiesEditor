package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.LogsFileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/download")
public class LoggerFilesController {

    private static final String AUDIT_FILE_NOT_EXISTS = "Audit file not exists!";

    private LogsFileService logsFileService;

    public LoggerFilesController(LogsFileService logsFileService) {
        this.logsFileService = logsFileService;
    }

    @GetMapping("/audit")
    @ResponseBody
    FileSystemResource downloadAuditFile(HttpServletResponse response) {
        File file = this.logsFileService.getAuditLogFile();
        if (file.exists()) {
            response.addHeader("Content-Disposition", "attachment; filename=" + LogsFileService.AUDIT_FILE + LogsFileService.FILE_LOG_EXT);
            response.setContentType("application/octet-stream");
        } else {
            try {
                response.sendRedirect("/properties?message=" + LoggerFilesController.AUDIT_FILE_NOT_EXISTS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new FileSystemResource(file);
    }

    @GetMapping("/application")
    @ResponseBody
    FileSystemResource downloadApplicationFile(HttpServletResponse response) {
        File file = this.logsFileService.getApplicationLogFile();
        if (file.exists()) {
            response.addHeader("Content-Disposition", "attachment; filename=" + LogsFileService.APPLICATION_FILE + LogsFileService.FILE_LOG_EXT);
            response.setContentType("application/octet-stream");
        } else {
            try {
                response.sendRedirect("/properties?message=" + LoggerFilesController.AUDIT_FILE_NOT_EXISTS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new FileSystemResource(file);
    }
}
