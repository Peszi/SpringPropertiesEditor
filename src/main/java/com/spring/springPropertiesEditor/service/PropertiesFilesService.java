package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.util.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface PropertiesFilesService {
    boolean loadPropertiesFile(MultipartFile multipartFile) throws FileNotFoundException;
    byte[] getPropertiesByteArray(FileType fileType) throws FileNotFoundException;
    String getPropertiesFileName(FileType fileType);
}
