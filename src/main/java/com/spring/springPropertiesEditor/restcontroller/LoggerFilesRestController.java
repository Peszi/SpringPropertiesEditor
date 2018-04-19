package com.spring.springPropertiesEditor.restcontroller;

import com.spring.springPropertiesEditor.service.LoggerFilesService;
import com.spring.springPropertiesEditor.util.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/logger")
public class LoggerFilesRestController {

    private LoggerFilesService loggerFilesService;

    public LoggerFilesRestController(LoggerFilesService loggerFilesService) {
        this.loggerFilesService = loggerFilesService;
    }

    @GetMapping("/audit")
    ResponseEntity getAuditFile() throws IOException {
        return this.setupResponse(this.loggerFilesService.getAuditLogFile());
    }

    @GetMapping("/application")
    ResponseEntity getApplicationFile() throws IOException {
        return this.setupResponse(this.loggerFilesService.getApplicationLogFile());
    }

    private ResponseEntity setupResponse(File file) throws IOException {
        final byte[] fileBytesArray = Files.readAllBytes(file.toPath());
        return ResponseEntity.ok()
                .contentLength(fileBytesArray.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .body(fileBytesArray);
    }
}
