package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AuditLoggerServiceImpl implements AuditLoggerService {

    private static final org.slf4j.Logger auditLogger = org.slf4j.LoggerFactory.getLogger(AuditLoggerServiceImpl.class);

    private static final String ADD_PREFIX = "[ADD]";
    private static final String EDIT_PREFIX = "[EDT]";
    private static final String REMOVE_PREFIX = "[RMV]";
    private static final String DATA_SEPARATOR = "###";
    private static final String VALUE_SEPARATOR = "=";

    @Value("${logging.path}")
    private String loggerPath;

    private List<String> auditLogsList;

    @Override
    public void loadAuditLog() {
        this.auditLogsList = this.loadLogsList(LogsFileServiceImpl.AUDIT_FILE + LogsFileServiceImpl.FILE_LOG_EXT);
    }

    @Override
    public void logPropertyCreated(Property property) {
        auditLogger.info(this.ADD_PREFIX + property.getKey() + this.DATA_SEPARATOR + property.getValue());
        this.loadAuditLog();
    }

    @Override
    public void logPropertyEdited(Property property, String oldValue) {
        auditLogger.info(this.EDIT_PREFIX + property.getKey() + this.DATA_SEPARATOR + oldValue + this.VALUE_SEPARATOR + property.getValue());
        this.loadAuditLog();
    }

    @Override
    public void logPropertyRemoved(Property property) {
        auditLogger.info(this.REMOVE_PREFIX + property.getKey() + this.DATA_SEPARATOR + property.getValue());
        this.loadAuditLog();
    }

    @Override
    public List<String> getAuditLogList(boolean forceLoad) {
        if (this.auditLogsList == null || forceLoad)
            this.loadAuditLog();
        return this.auditLogsList;
    }

    private List<String> loadLogsList(String path) {
        try {
            return Files.readAllLines(Paths.get(this.loggerPath + "/" + path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
