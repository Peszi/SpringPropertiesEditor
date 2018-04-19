package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.LoggerFilesService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/logger")
public class LoggerFilesController {

    private LoggerFilesService loggerFilesService;

    public LoggerFilesController(LoggerFilesService loggerFilesService) {
        this.loggerFilesService = loggerFilesService;
    }

    @GetMapping("/audit")
    @ResponseBody
    FileSystemResource getAuditFile(HttpServletResponse response) throws FileNotFoundException {
        return this.getLoggerFile(response, this.loggerFilesService.getAuditLogFile());
    }

    @GetMapping("/application")
    @ResponseBody
    FileSystemResource getApplicationFile(HttpServletResponse response) throws FileNotFoundException {
        return this.getLoggerFile(response, this.loggerFilesService.getApplicationLogFile());
    }

    private FileSystemResource getLoggerFile(HttpServletResponse response, File file) {
        response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setContentType("application/octet-stream");
        return new FileSystemResource(file);
    }
}
