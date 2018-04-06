package com.spring.springPropertiesEditor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class AuditLoggerService {

    private static final org.slf4j.Logger auditLogger = org.slf4j.LoggerFactory.getLogger(AuditLoggerService.class);

    private static final String ADD_PREFIX = "[ADD]";
    private static final String EDIT_PREFIX = "[EDT]";
    private static final String REMOVE_PREFIX = "[RMV]";
    private static final String DATA_SEPARATOR = "###";
    private static final String VALUE_SEPARATOR = "=";

    @Value("${logging.path}")
    private String loggerPath;

    private List<String> auditLogsList;

    public void logInit() {
        this.loadAuditLog();
    }

    public void logPropertyCreated(String key, String value) {
        auditLogger.info(this.ADD_PREFIX + key + this.DATA_SEPARATOR + value);
        this.loadAuditLog();
    }

    public void logPropertyEdited(String key, String oldValue, String newValue) {
        auditLogger.info(this.EDIT_PREFIX + key + this.DATA_SEPARATOR + oldValue + this.VALUE_SEPARATOR + newValue);
        this.loadAuditLog();
    }

    public void logPropertyRemoved(String key, String value) {
        auditLogger.info(this.REMOVE_PREFIX + key + this.DATA_SEPARATOR + value);
        this.loadAuditLog();
    }

    public void loadAuditLog() {
        this.auditLogsList = this.loadLogsList(LogsFileService.AUDIT_FILE + LogsFileService.FILE_LOG_EXT);
    }

    private List<String> loadLogsList(String path) {
        try {
            return Files.readAllLines(Paths.get(this.loggerPath + "/" + path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAuditLogList(boolean forceLoad) {
        if (this.auditLogsList == null || forceLoad)
            this.loadAuditLog();
        return this.auditLogsList;
    }
}
