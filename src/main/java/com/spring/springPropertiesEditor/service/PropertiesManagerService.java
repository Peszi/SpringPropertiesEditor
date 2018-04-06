package com.spring.springPropertiesEditor.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class PropertiesManagerService {

    private AuditLoggerService auditLoggerService;

    private Properties properties;
    private boolean areLoaded;

    public PropertiesManagerService(AuditLoggerService auditLoggerService) {
        this.auditLoggerService = auditLoggerService;
        this.properties = new Properties();
    }

    // Manage properties

    public boolean loadProperties(InputStream inputStream) {
        try {
            this.properties.clear();
            this.properties.load(inputStream);
            this.areLoaded = true;
            this.auditLoggerService.logInit();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveProperties(OutputStream outputStream) {
        try {
            this.properties.store(outputStream, "Modified");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean areLoaded() {
        return this.areLoaded;
    }

    // Modify properties

    public void modifyProperty(String key, String value) {
        if (this.hasKey(key)) {
            this.editProperty(key, value);
        } else {
            this.addProperty(key, value);
        }
    }

    public void addProperty(String key, String value) {
        this.properties.setProperty(key.trim(), value.trim());
        this.auditLoggerService.logPropertyCreated(key, value);
    }

    public boolean editProperty(String key, String newValue) {
        final String oldValue = this.properties.getProperty(key.trim());
        if (!newValue.trim().equals(oldValue) && this.properties.replace(key, oldValue, newValue.trim())) {
            this.auditLoggerService.logPropertyEdited(key, oldValue, newValue);
            return true;
        }
        return false;
    }

    public boolean removeProperty(String key, String value) {
        if (this.properties.remove(key.trim(), value.trim())) {
            this.auditLoggerService.logPropertyRemoved(key, value);
            return true;
        }
        return false;
    }

    // Getters

    public boolean hasKey(String key) {
        return this.properties.containsKey(key);
    }

    public Properties getProperties() {
        return properties;
    }

    public Map<String, String> getSortedPropertiesList() {
        Map<String, String> propertiesMap = new HashMap<>();
        for (String key : new TreeSet<>(this.properties.stringPropertyNames()).descendingSet())
            propertiesMap.put(key, this.properties.getProperty(key));
        return propertiesMap;
    }

    public Object getAuditLogList() {
        return this.auditLoggerService.getAuditLogList(false);
    }
}
