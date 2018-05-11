package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;

import java.util.List;

public interface ManagePropertiesService {
    void addOrChangeProperty(Property property);
    void removeProperty(Property property);
    Property getProperty(String key);
    List<Property> getAllProperties();
    List<String> getAllAuditLogs();
}
