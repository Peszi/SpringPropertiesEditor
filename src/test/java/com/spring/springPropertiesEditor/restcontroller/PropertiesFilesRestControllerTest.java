package com.spring.springPropertiesEditor.restcontroller;

import com.spring.springPropertiesEditor.service.PropertiesFilesService;
import com.spring.springPropertiesEditor.util.FileType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PropertiesFilesRestControllerTest {

    private static final String API_PATH = "/api/properties";

    private static final String SOME_PROPERTIES = "spring.h2.console.enabled=true";
    private static final String FILE_NAME = "application.properties";

    @Mock
    PropertiesFilesService filesService;

    PropertiesFilesRestController filesController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.filesController = new PropertiesFilesRestController(filesService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.filesController).build();
    }

    @Test
    public void uploadFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", FILE_NAME, "multipart/form-data", API_PATH.getBytes());

        when(filesService.loadPropertiesFile(any(MultipartFile.class))).thenReturn(true);

        this.mockMvc.perform(multipart(API_PATH + "/upload").file(multipartFile))
                .andExpect(status().is2xxSuccessful());

        verify(filesService, times(1)).loadPropertiesFile(any(MultipartFile.class));
    }

    @Test
    public void uploadIncorrectFile() throws Exception {
        when(filesService.loadPropertiesFile(any(MultipartFile.class))).thenReturn(true);

        this.mockMvc.perform(multipart(API_PATH + "/upload"))
                .andExpect(status().is4xxClientError());

        verify(filesService, times(0)).loadPropertiesFile(any(MultipartFile.class));
    }

    @Test
    public void downloadPropertiesFile() throws Exception {

        FileType fileType = FileType.PROPERTIES;
        when(filesService.getPropertiesFileName(fileType)).thenReturn(FILE_NAME);
        when(filesService.getPropertiesByteArray(fileType)).thenReturn(SOME_PROPERTIES.getBytes());

        MvcResult result = this.mockMvc.perform(get(API_PATH + "/download"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("Content-Disposition", "attachment; filename=" + FILE_NAME))
                .andExpect(header().string("Content-Type", "application/octet-stream"))
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        verify(filesService, times(1)).getPropertiesByteArray(any(FileType.class));
        assertEquals(SOME_PROPERTIES, resultContent);
    }

    @Test
    public void downloadJsonAudit() throws Exception {

        FileType fileType = FileType.JSON;
        when(filesService.getPropertiesFileName(fileType)).thenReturn(FILE_NAME);
        when(filesService.getPropertiesByteArray(fileType)).thenReturn(SOME_PROPERTIES.getBytes());

        MvcResult result = this.mockMvc.perform(get(API_PATH + "/download/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("Content-Disposition", "attachment; filename=" + FILE_NAME))
                .andExpect(header().string("Content-Type", "application/octet-stream"))
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        verify(filesService, times(1)).getPropertiesByteArray(any(FileType.class));
        assertEquals(SOME_PROPERTIES, resultContent);
    }

    @Test
    public void downloadYamlAudit() throws Exception {

        FileType fileType = FileType.YAML;
        when(filesService.getPropertiesFileName(fileType)).thenReturn(FILE_NAME);
        when(filesService.getPropertiesByteArray(fileType)).thenReturn(SOME_PROPERTIES.getBytes());

        MvcResult result = this.mockMvc.perform(get(API_PATH + "/download/yaml"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("Content-Disposition", "attachment; filename=" + FILE_NAME))
                .andExpect(header().string("Content-Type", "application/octet-stream"))
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        verify(filesService, times(1)).getPropertiesByteArray(any(FileType.class));
        assertEquals(SOME_PROPERTIES, resultContent);
    }
}