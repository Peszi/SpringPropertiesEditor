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
        this.readPropertiesFromFile();
    }

    public Properties getProperties() {
        return properties;
    }

    public TreeMap<String, String> printProperties() {
        TreeMap<String, String> propertiesMap = new TreeMap<>();
        Enumeration<String> propertyNamesArray = (Enumeration<String>) this.properties.propertyNames();
        while(propertyNamesArray.hasMoreElements()) {
            final String propName = propertyNamesArray.nextElement();
            propertiesMap.put(propName, this.properties.getProperty(propName));
        }
        return propertiesMap;
    }

    public void setProperty(String key, String value) {
        logger.info("setProperty " + key + " = " + value);
        this.properties.setProperty(key, value);
    }

    public void readPropertiesFromFile() { // TODO TMP
        logger.info("LOAD PATH " + this.file_path + FILE_PROPERTIES_EXT);
        this.readPropertiesFromFile(getClass().getClassLoader().getResource(PROPERTIES_PATH + FILE_PROPERTIES_EXT).getFile());
    }

    private void readPropertiesFromFile(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            this.defaultPropertiesPersister.load(this.properties, fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void savePropertiesAsJson(String filePath) {
        try {
            final String jsonProperties = new ObjectMapper().writeValueAsString(this.properties);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath + FILE_JSON_EXT));
            fileOutputStream.write(jsonProperties.getBytes());
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

    public void savePropertiesAsYaml(String filePath) {
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
