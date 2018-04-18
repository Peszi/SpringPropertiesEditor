package com.spring.springPropertiesEditor.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class PropertiesFileServiceImpl implements PropertiesFileService {

    public static final String FILE_JSON_EXT = ".json";
    public static final String FILE_YAML_EXT = ".yaml";

    private PropertiesService propertiesService;

    private String propertiesFileName;

    public PropertiesFileServiceImpl(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    @Override
    public boolean isFileLoaded() {
        return (this.propertiesFileName != null) ? true : false;
    }

    @Override
    public String getFileName() {
        return this.propertiesFileName;
    }

    // Read

    @Override
    public boolean loadPropertiesFile(MultipartFile multipartFile) {
        try {
            if (multipartFile != null && this.checkFileExtension(multipartFile.getOriginalFilename())) {
                this.propertiesFileName = multipartFile.getOriginalFilename();
                return this.propertiesService.loadProperties(multipartFile.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Write

    @Override
    public void getPropertiesStream(OutputStream outputStream) {
        this.propertiesService.saveProperties(outputStream);
    }

    @Override
    public boolean getJsonStream(OutputStream outputStream) {
        try {
            new ObjectMapper(new JsonFactory()).writeValue(outputStream, this.propertiesService.getProperties());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean getYamlStream(OutputStream outputStream) {
        try {
            new ObjectMapper(new YAMLFactory()).writeValue(outputStream, this.propertiesService.getProperties());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkFileExtension(String fullPath) {
        return (fullPath.endsWith(".properties") ? true : false);
    }

    public String getJsonFileName() {
        return this.propertiesFileName.split("\\.")[0] + PropertiesFileServiceImpl.FILE_JSON_EXT;
    }

    public String getYamlFileName() {
        return this.propertiesFileName.split("\\.")[0] + PropertiesFileServiceImpl.FILE_YAML_EXT;
    }
}
