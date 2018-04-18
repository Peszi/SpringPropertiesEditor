package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;

import java.util.List;

public interface AuditLoggerService {
    void loadAuditLog();
    void logPropertyCreated(Property property);
    void logPropertyEdited(Property property, String oldValue);
    void logPropertyRemoved(Property property);
    List<String> getAuditLogList(boolean forceLoad);
}
