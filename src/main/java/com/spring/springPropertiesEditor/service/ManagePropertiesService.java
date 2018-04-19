package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;

import java.util.List;
import java.util.Map;

public interface ManagePropertiesService {
    void addOrChangeProperty(Property property);
    void removeProperty(Property property);
    Property getProperty(String key);
    Map<String, String> getAllProperties();
    List<String> getAllAuditLogs();
}
