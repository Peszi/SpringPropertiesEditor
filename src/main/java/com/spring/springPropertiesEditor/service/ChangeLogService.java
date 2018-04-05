package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.logger.AuditLogger;
import org.springframework.stereotype.Component;

import java.io.OutputStream;

@Component
public class ChangeLogService {

    public static org.slf4j.Logger auditLogger = org.slf4j.LoggerFactory.getLogger(ChangeLogService.class);

    private static final String ADD_PREFIX = "[ADD]";
    private static final String EDIT_PREFIX = "[EDT]";
    private static final String REMOVE_PREFIX = "[RMV]";
    private static final String DATA_SEPARATOR = "###";
    private static final String VALUE_SEPARATOR = "=";

    public void logPropertyCreate(String key, String value) {
        auditLogger.info(this.ADD_PREFIX + key + this.DATA_SEPARATOR + value);
    }

    public void logPropertyEdit(String key, String oldValue, String newValue) {
        auditLogger.info(this.EDIT_PREFIX + key + this.DATA_SEPARATOR + oldValue + this.VALUE_SEPARATOR + newValue);
    }

    public void logPropertyRemove(String key, String value) {
        auditLogger.info(this.REMOVE_PREFIX + key + this.DATA_SEPARATOR + value);
    }

    // R/W files

    public boolean getChangesJsonStream(OutputStream outputStream) {
//        try {
//            new ObjectMapper().writeValue(outputStream, this.changeChangeLogRepository.findAll());
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    public boolean getChangesYamlStream(OutputStream outputStream) {
//        try {
//            new ObjectMapper(new YAMLFactory()).writeValue(outputStream, this.changeChangeLogRepository.findAll());
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    public boolean isEmpty() {
        return false;
    }
}
