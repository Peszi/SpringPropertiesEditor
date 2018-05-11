package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.exception.BadRequestException;
import com.spring.springPropertiesEditor.model.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ManagePropertiesServiceImpl implements ManagePropertiesService {

    private AuditLoggerService auditLoggerService;
    private PropertiesService propertiesService;

    public ManagePropertiesServiceImpl(AuditLoggerService auditLoggerService, PropertiesService propertiesService) {
        this.auditLoggerService = auditLoggerService;
        this.propertiesService = propertiesService;
    }

    @Override
    public void addOrChangeProperty(Property property) {
        if (!this.propertiesService.hasKey(property.getKey())) {
            this.propertiesService.addProperty(property);
            this.auditLoggerService.logPropertyCreated(property);
        } else {
            Optional<String> oldValue = this.propertiesService.editProperty(property);
            if (oldValue.isPresent()) {
                this.auditLoggerService.logPropertyEdited(property, oldValue.get());
            } else {
                throw new BadRequestException();
            }
        }
    }

    @Override
    public void removeProperty(Property property) {
        if (!this.propertiesService.removeProperty(property))
            throw new BadRequestException("Cannot remove this property!");
        this.auditLoggerService.logPropertyRemoved(property);
    }

    @Override
    public Property getProperty(String key) {
        if (key != null && this.propertiesService.hasKey(key))
            return new Property(key, this.propertiesService.getProperties().getProperty(key));
        return new Property();
    }

    @Override
    public List<Property> getAllProperties() {
        List<Property> propertiesList = new ArrayList<>();
        for (String key : new TreeSet<>(this.propertiesService.getProperties().stringPropertyNames()).descendingSet())
            propertiesList.add(new Property(key, this.propertiesService.getProperties().getProperty(key)));
        return propertiesList;
    }

    @Override
    public List<String> getAllAuditLogs() {
        return this.auditLoggerService.getAuditLogList(false);
    }
}
