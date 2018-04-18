package com.spring.springPropertiesEditor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.spring.springPropertiesEditor.controller.PropertiesFilesController;
import com.spring.springPropertiesEditor.util.FileType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PropertiesFilesServiceImplTest {

    private static final String FILE_NAME = "application.properties";
    private static final String SOME_PROPERTIES = "spring.h2.console.enabled=true";

    @Mock
    PropertiesService propertiesService;

    PropertiesFilesServiceImpl propertiesFilesService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.propertiesFilesService = new PropertiesFilesServiceImpl(propertiesService);
    }

    @Test
    public void loadPropertiesFile() throws FileNotFoundException {
        MockMultipartFile multipartFile = new MockMultipartFile("file", FILE_NAME, "text/plain", SOME_PROPERTIES.getBytes());

        when(propertiesService.loadProperties(any(InputStream.class))).thenReturn(true);

        boolean result = this.propertiesFilesService.loadPropertiesFile(multipartFile);
        String fileName = this.propertiesFilesService.getPropertiesFileName(FileType.PROPERTIES);

        verify(propertiesService, times(1)).loadProperties(any(InputStream.class));
        assertTrue(result);
        assertEquals(FILE_NAME, fileName);
    }

    @Test
    public void loadPropertiesJsonFile() throws FileNotFoundException {
        final String JSON_FILE_NAME = FILE_NAME.split("\\.")[0] + ".json";
        MockMultipartFile multipartFile = new MockMultipartFile("file", FILE_NAME, "text/plain", SOME_PROPERTIES.getBytes());

        when(propertiesService.loadProperties(any(InputStream.class))).thenReturn(true);

        boolean result = this.propertiesFilesService.loadPropertiesFile(multipartFile);
        String fileName = this.propertiesFilesService.getPropertiesFileName(FileType.JSON);

        verify(propertiesService, times(1)).loadProperties(any(InputStream.class));
        assertTrue(result);
        assertEquals(JSON_FILE_NAME, fileName);
    }

    @Test
    public void getPropertiesByteArray() throws IOException {
        when(propertiesService.saveProperties(any(OutputStream.class))).thenReturn(true);
        this.propertiesFilesService.getPropertiesByteArray(FileType.PROPERTIES);
    }

    @Test(expected = FileNotFoundException.class)
    public void getPropertiesByteArrayFail() throws FileNotFoundException {
        when(propertiesService.saveProperties(any(OutputStream.class))).thenReturn(false);
        this.propertiesFilesService.getPropertiesByteArray(FileType.PROPERTIES);
    }

    @Test
    public void getJsonByteArray() throws IOException {
        Properties properties = new Properties();
        properties.put("some.property", "value");

        when(propertiesService.getProperties()).thenReturn(properties);

        final byte[] propertiesArray = this.propertiesFilesService.getPropertiesByteArray(FileType.JSON);

        assertEquals(new ObjectMapper().writeValueAsString(properties), new String(propertiesArray));
    }

    @Test
    public void getYamlByteArray() throws IOException {
        Properties properties = new Properties();
        properties.put("some.property", "value");

        when(propertiesService.getProperties()).thenReturn(properties);

        final byte[] propertiesArray = this.propertiesFilesService.getPropertiesByteArray(FileType.YAML);

        assertEquals(new ObjectMapper(new YAMLFactory()).writeValueAsString(properties), new String(propertiesArray));
    }
}