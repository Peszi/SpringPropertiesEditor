package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;

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
        return Optional.ofNullable((String) this.properties.replace(property.getKey(), property.getValue()));
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
