package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public interface PropertiesService {
    void addProperty(Property property);
    Optional<String> editProperty(Property property);
    boolean removeProperty(Property property);
    boolean loadProperties(InputStream inputStream);
    boolean saveProperties(OutputStream outputStream);
    boolean hasKey(String key);
}
