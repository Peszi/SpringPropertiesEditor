package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface ManagePropertiesService {
    boolean addOrChangeProperty(Property property);
    boolean removeProperty(Property property);
    Property getProperty(String key);
    Map<String, String> getAllProperties();
    List<String> getAllAuditLogs();
}
