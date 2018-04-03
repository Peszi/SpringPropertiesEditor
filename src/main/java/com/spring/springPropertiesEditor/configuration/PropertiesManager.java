package com.spring.springPropertiesEditor.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.spring.springPropertiesEditor.controller.UsersController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Component
public class PropertiesManager {

    private final static Logger logger = LoggerFactory.getLogger(UsersController.class);

    private static final String PROPERTIES_PATH = "users";
    private static final String FILE_PROPERTIES_EXT = ".properties";
    private static final String FILE_JSON_EXT = ".json";
    private static final String FILE_YAML_EXT = ".yaml";

    private String file_path = "C:\\Users\\mamo\\Desktop\\" + PROPERTIES_PATH;

    private Environment environment;

    private Properties properties;
    private DefaultPropertiesPersister defaultPropertiesPersister;

    public PropertiesManager(Environment environment) {
        this.environment = environment;
        this.properties = new Properties();
        this.defaultPropertiesPersister = new DefaultPropertiesPersister();
    }

    @PostConstruct
    public void init() {
        this.loadPropertiesFile();

//        this.textWriteYaml(this.file_path);
    }

//    private void textWriteYaml(String filePath) {
//        Property property = new Property();
//        property.setKey("key");
//        property.setValue(123);
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath + FILE_YAML_EXT));
//            new ObjectMapper(new YAMLFactory()).writeValue(fileOutputStream, property);
//            fileOutputStream.flush();
//            fileOutputStream.close();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public Properties getProperties() {
        return properties;
    }

    public TreeMap<String, String> getPropertiesList() {
        TreeMap<String, String> propertiesMap = new TreeMap<>();
        Enumeration<String> propertyNamesArray = (Enumeration<String>) this.properties.propertyNames();
        while(propertyNamesArray.hasMoreElements()) {
            final String propName = propertyNamesArray.nextElement();
            propertiesMap.put(propName, this.properties.getProperty(propName));
        }
        return propertiesMap;
    }

    public void addProperty(String key, String value) {
        logger.info("Adding property: " + key + "###" + value);
        this.properties.setProperty(key, value);
    }

    public boolean editProperty(String key, String newValue) {
        final String oldValue = this.properties.getProperty(key);
        logger.info("Editing property: " + key + "###" + oldValue + " TO " + newValue);
        return this.properties.replace(key, oldValue, newValue);
    }

    public boolean removeProperty(String key, String value) {
        logger.info("Removing property: " + key + "###" + value);
        return this.properties.remove(key, value);
    }

    public boolean isPropertyKeyIn(String key) {
        return (this.properties.containsKey(key) ? true : false);
    }

    public void loadPropertiesFile() { // TODO TMP
        logger.info("LOAD PATH " + this.file_path + FILE_PROPERTIES_EXT);
//        this.loadPropertiesFile(new File(getClass().getClassLoader().getResource(PROPERTIES_PATH + FILE_PROPERTIES_EXT).getFile()));
    }

    public boolean loadPropertiesFile(MultipartFile multipartFile) {
        try {
            if (this.checkExtension(multipartFile.getOriginalFilename())) {
                this.properties.load(multipartFile.getInputStream());
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkExtension(String fullPath) {
        return (fullPath.endsWith(".properties") ? true : false);
    }

    public void savePropertiesAsProperties() { // TODO TMP
        this.savePropertiesAsProperties(this.file_path);
        this.savePropertiesAsJson(this.file_path);
        this.savePropertiesAsYaml(this.file_path);
    }

    private void savePropertiesAsProperties(String filePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath + FILE_PROPERTIES_EXT));
            this.defaultPropertiesPersister.store(this.properties, fileOutputStream, "");
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePropertiesAsJson(String filePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath + FILE_JSON_EXT));
            fileOutputStream.write(new ObjectMapper().writeValueAsString(this.properties).getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePropertiesAsYaml(String filePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath + FILE_YAML_EXT));
            new ObjectMapper(new YAMLFactory()).writeValue(fileOutputStream, this.properties);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
