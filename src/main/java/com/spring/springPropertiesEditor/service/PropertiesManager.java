package com.spring.springPropertiesEditor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Component
public class PropertiesManager {

    private final static Logger logger = LoggerFactory.getLogger(PropertiesManager.class);

    private ChangeLogService changeLogService;

    private Properties properties;
    private String propertiesFileName;

    public PropertiesManager(ChangeLogService changeLogService) {
        this.changeLogService = changeLogService;
        this.properties = new Properties();
    }

    // Modify properties

    public void addProperty(String key, String value) {
        this.properties.setProperty(key.trim(), value.trim());
        this.changeLogService.logPropertyCreate(key, value);
    }

    public boolean editProperty(String key, String newValue) {
        final String oldValue = this.properties.getProperty(key.trim());
        if (!newValue.trim().equals(oldValue) && this.properties.replace(key, oldValue, newValue.trim())) {
            this.changeLogService.logPropertyEdit(key, oldValue, newValue);
            return true;
        }
        return false;
    }

    public boolean removeProperty(String key, String value) {
        if (this.properties.remove(key.trim(), value.trim())) {
            this.changeLogService.logPropertyRemove(key, value);
            return true;
        }
        return false;
    }

    // R/W files

    public boolean loadPropertiesFile(MultipartFile multipartFile) {
        try {
            if (multipartFile != null && this.checkExtension(multipartFile.getOriginalFilename())) {
                this.propertiesFileName = multipartFile.getOriginalFilename();
                this.properties.clear();
                this.properties.load(multipartFile.getInputStream());
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getPropertiesOutputStream(OutputStream outputStream) {
        try {
            this.properties.store(outputStream, "Modified");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Deprecated
    private void savePropertiesFile(String filePath) { // TODO
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
            this.properties.store(fileOutputStream, "Modified");
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters

    private boolean checkExtension(String fullPath) {
        return (fullPath.endsWith(".properties") ? true : false);
    }

    public boolean isLoaded() {
        return (this.propertiesFileName != null) ? true : false;
    }

    public boolean hasKey(String key) {
        return this.properties.containsKey(key);
    }

    public String getPropertiesFileName() {
        return this.propertiesFileName;
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

}
