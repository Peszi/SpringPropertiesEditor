package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.exception.BadRequestException;
import com.spring.springPropertiesEditor.model.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Service
public class PropertiesServiceImpl implements PropertiesService {

    private Properties properties;

    public PropertiesServiceImpl(@Qualifier("UserProperties") Properties properties) {
        this.properties = properties;
    }

    // R/W

    @Override
    public boolean loadProperties(InputStream inputStream) {
        try {
            this.properties.clear();
            this.properties.load(inputStream);
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
        this.properties.replace(property.getKey(), oldValue, property.getValue());
        return Optional.of(oldValue);
    }

    @Override
    public boolean removeProperty(Property property) {
        return this.properties.remove(property.getKey(), property.getValue());
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
