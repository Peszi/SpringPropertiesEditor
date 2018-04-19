package com.spring.springPropertiesEditor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class LoggerFilesServiceImpl implements LoggerFilesService {

    public static final String AUDIT_FILE = "Audit";
    public static final String APPLICATION_FILE = "Application";
    public static final String FILE_LOG_EXT = ".log";

    @Value("${logging.path}")
    private String loggerPath;

    @Override
    public File getAuditLogFile() throws FileNotFoundException {
        return this.getFile(LoggerFilesServiceImpl.AUDIT_FILE + LoggerFilesServiceImpl.FILE_LOG_EXT);
    }

    @Override
    public File getApplicationLogFile() throws FileNotFoundException {
        return this.getFile(LoggerFilesServiceImpl.APPLICATION_FILE + LoggerFilesServiceImpl.FILE_LOG_EXT);
    }

    private File getFile(String fileName) throws FileNotFoundException {
        File file = new File(this.loggerPath + "/" + fileName);
        if (file.exists())
            return file;
        throw new FileNotFoundException("File with path: " + fileName + " was not found!");
    }
}
