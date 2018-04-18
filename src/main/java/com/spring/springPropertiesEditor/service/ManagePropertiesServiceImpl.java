package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class ManagePropertiesServiceImpl implements ManagePropertiesService {

    private AuditLoggerService auditLoggerService;
    private PropertiesService propertiesService;

    public ManagePropertiesServiceImpl(AuditLoggerService auditLoggerService, PropertiesService propertiesService) {
        this.auditLoggerService = auditLoggerService;
        this.propertiesService = propertiesService;
    }

    @Override
    public boolean addOrChangeProperty(Property property) {
        if (!this.propertiesService.hasKey(property.getKey())) {
            this.propertiesService.addProperty(property);
            this.auditLoggerService.logPropertyCreated(property);
            return true;
        } else {
            Optional<String> oldValue = this.propertiesService.editProperty(property);
            if (oldValue.isPresent()) {
                this.auditLoggerService.logPropertyEdited(property, oldValue.get());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeProperty(Property property) {
        if (this.propertiesService.removeProperty(property)) {
            this.auditLoggerService.logPropertyRemoved(property);
            return true;
        }
        return false;
    }

    @Override
    public Property getProperty(String key) {
        if (key != null && this.propertiesService.hasKey(key))
            return new Property(key, this.propertiesService.getProperties().getProperty(key));
        return new Property();
    }

    @Override
    public Map<String, String> getAllProperties() {
        Map<String, String> propertiesMap = new HashMap<>();
        for (String key : new TreeSet<>(this.propertiesService.getProperties().stringPropertyNames()).descendingSet())
            propertiesMap.put(key, this.propertiesService.getProperties().getProperty(key));
        return propertiesMap;
    }

    @Override
    public List<String> getAllAuditLogs() {
        return this.auditLoggerService.getAuditLogList(false);
    }
}