package com.spring.springPropertiesEditor.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.spring.springPropertiesEditor.util.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class PropertiesFilesServiceImpl implements PropertiesFilesService {

    public static final String FILE_JSON_EXT = ".json";
    public static final String FILE_YAML_EXT = ".yaml";

    private PropertiesService propertiesService;

    private String propertiesFileName;

    public PropertiesFilesServiceImpl(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    private void setFileName(String propertiesFileName) {
        this.propertiesFileName = propertiesFileName;
    }

    // Properties loader
    @Override
    public boolean loadPropertiesFile(MultipartFile multipartFile) throws FileNotFoundException {
        try {
            if (multipartFile != null && multipartFile.getOriginalFilename().endsWith(".properties")) {
                this.setFileName(multipartFile.getOriginalFilename());
                return this.propertiesService.loadProperties(multipartFile.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new FileNotFoundException("Cannot upload this file!");
    }

    // Getter properties array
    @Override
    public byte[] getPropertiesByteArray(FileType fileType) throws FileNotFoundException {
        switch (fileType) {
            case JSON:
                return this.getMapperByteArray(new ObjectMapper(new JsonFactory()));
            case YAML:
                return this.getMapperByteArray(new ObjectMapper(new YAMLFactory()));
        }
        return this.getPropertiesByteArray();
    }

    private byte[] getPropertiesByteArray() throws FileNotFoundException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (this.propertiesService.saveProperties(outputStream))
            return outputStream.toByteArray();
        throw new FileNotFoundException("Cannot find properties file!");
    }

    private byte[] getMapperByteArray(ObjectMapper objectMapper) throws FileNotFoundException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectMapper.writeValue(byteArrayOutputStream, this.propertiesService.getProperties());
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new FileNotFoundException("Cannot find properties file!");
    }

    // Getter file name
    @Override
    public String getPropertiesFileName(FileType fileType) {
        if (!fileType.equals(FileType.PROPERTIES)) {
            final String fileSuffix = this.propertiesFileName.split("\\.")[0];
            switch (fileType) {
                case JSON:
                    return fileSuffix + PropertiesFilesServiceImpl.FILE_JSON_EXT;
                case YAML:
                    return fileSuffix + PropertiesFilesServiceImpl.FILE_YAML_EXT;
            }
        }
        return this.propertiesFileName;
    }
}
