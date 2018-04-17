package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;

import java.util.Map;

public interface ManagePropertiesService {
    boolean addOrChangeProperty(Property property);
    boolean removeProperty(Property property);
    Map<String, String> getAllProperties();
}
