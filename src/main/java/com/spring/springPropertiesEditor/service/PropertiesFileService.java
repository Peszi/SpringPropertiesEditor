package com.spring.springPropertiesEditor.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

public interface PropertiesFileService {
    boolean isFileLoaded();
    String getFileName();
    boolean loadPropertiesFile(MultipartFile multipartFile);
    void getPropertiesStream(OutputStream outputStream);
    boolean getJsonStream(OutputStream outputStream);
    boolean getYamlStream(OutputStream outputStream);
}
