package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;

public interface PropertiesService {
    boolean loadProperties(InputStream inputStream);
    boolean saveProperties(OutputStream outputStream);
    void addProperty(Property property);
    Optional<String> editProperty(Property property);
    boolean removeProperty(Property property);
    boolean hasKey(String key);
    Properties getProperties();
}
