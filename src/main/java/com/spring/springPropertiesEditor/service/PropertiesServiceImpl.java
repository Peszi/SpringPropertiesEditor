package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;

@Component
public class PropertiesServiceImpl implements PropertiesService {

    private Properties properties;
    private boolean areLoaded;

    public PropertiesServiceImpl() {
        this.properties = new Properties();
        this.areLoaded = false;
    }

    // R/W

    @Override
    public boolean loadProperties(InputStream inputStream) {
        try {
            this.properties.clear();
            this.properties.load(inputStream);
            this.areLoaded = true;
//            this.auditLoggerService.logInit();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveProperties(OutputStream outputStream) {
        try {
            this.properties.store(outputStream, "Modified");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Modify

    @Override
    public void addProperty(Property property) {
        this.properties.setProperty(property.getKey(), property.getValue());
    }

    @Override
    public Optional<String> editProperty(Property property) {
        final String oldValue = this.properties.getProperty(property.getKey());
        if (!property.getValue().equals(oldValue) && this.properties.replace(property.getKey(), oldValue, property.getValue()))
            Optional.of(oldValue);
        return Optional.empty();
    }

    @Override
    public boolean removeProperty(Property property) {
        return this.properties.remove(property.getKey(), property.getValue());
    }

    public boolean arePropertiesLoaded() {
        return this.areLoaded;
    }

    @Override
    public boolean hasKey(String key) {
        return this.properties.containsKey(key);
    }

    @Override
    public Properties getProperties() {
        return this.properties;
    }
}
