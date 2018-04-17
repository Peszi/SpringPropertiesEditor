package com.spring.springPropertiesEditor.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class PropertiesFileService {

    public static final String FILE_JSON_EXT = ".json";
    public static final String FILE_YAML_EXT = ".yaml";

    private ManagePropertiesServiceImpl managePropertiesServiceImpl;

    private String propertiesFileName;

    public PropertiesFileService(ManagePropertiesServiceImpl managePropertiesServiceImpl) {
        this.managePropertiesServiceImpl = managePropertiesServiceImpl;
    }

    public boolean loadFile(MultipartFile multipartFile) {
        try {
            if (multipartFile != null && this.checkExtension(multipartFile.getOriginalFilename())) {
                this.propertiesFileName = multipartFile.getOriginalFilename();
                return this.managePropertiesServiceImpl.loadProperties(multipartFile.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getPropertiesStream(OutputStream outputStream) {
        this.managePropertiesServiceImpl.saveProperties(outputStream);
    }

    public boolean getJsonStream(OutputStream outputStream) {
        try {
            new ObjectMapper(new JsonFactory()).writeValue(outputStream, this.managePropertiesServiceImpl.getProperties());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getYamlStream(OutputStream outputStream) {
        try {
            new ObjectMapper(new YAMLFactory()).writeValue(outputStream, this.managePropertiesServiceImpl.getProperties());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkExtension(String fullPath) {
        return (fullPath.endsWith(".properties") ? true : false);
    }

    public boolean isLoaded() {
        return (this.propertiesFileName != null) ? true : false;
    }

    public String getFileName() {
        return this.propertiesFileName;
    }

    public String getJsonFileName() {
        return this.propertiesFileName.split("\\.")[0] + PropertiesFileService.FILE_JSON_EXT;
    }

    public String getYamlFileName() {
        return this.propertiesFileName.split("\\.")[0] + PropertiesFileService.FILE_YAML_EXT;
    }
}
