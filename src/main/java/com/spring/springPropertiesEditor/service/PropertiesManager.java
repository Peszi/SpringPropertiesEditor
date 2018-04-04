package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Component
public class PropertiesManager {

    private final static Logger logger = LoggerFactory.getLogger(PropertiesManager.class);

    private ChangesManager changesManager;

    private Properties properties;
    private String propertiesFileName;

    public PropertiesManager(ChangesManager changesManager) {
        this.changesManager = changesManager;
        this.properties = new Properties();
    }

    // Modify properties

    public void addProperty(String key, String value) {
        this.properties.setProperty(key, value);
        logger.info("Adding property: " + key + "###" + value);
        this.changesManager.addChange(new Log().setAddLog(key, value));
    }

    public boolean editProperty(String key, String newValue) {
        final String oldValue = this.properties.getProperty(key);
        if (this.properties.replace(key, oldValue, newValue)) {
            logger.info("Editing property: " + key + "###" + oldValue + " TO " + newValue);
            this.changesManager.addChange(new Log().setEditLog(key, oldValue, newValue));
            return true;
        }
        return false;
    }

    public boolean removeProperty(String key, String value) {
        if (this.properties.remove(key, value)) {
            logger.info("Removing property: " + key + "###" + value);
            this.changesManager.addChange(new Log().setRemoveLog(key, value));
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

    private void savePropertiesFile(String filePath) { // TODO
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
            this.properties.store(fileOutputStream, "Modified");
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public TreeMap<String, String> getSortedPropertiesList() {
        TreeMap<String, String> propertiesMap = new TreeMap<>();
        for (String key : this.properties.stringPropertyNames())
            propertiesMap.put(key, this.properties.getProperty(key));
        return propertiesMap;
    }

}
