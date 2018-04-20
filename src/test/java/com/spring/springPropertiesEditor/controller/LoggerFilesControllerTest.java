package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.restcontroller.LoggerFilesRestController;
import com.spring.springPropertiesEditor.service.LoggerFilesService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class LoggerFilesControllerTest {

    private static final String API_PATH = "/logger";

    @Mock
    LoggerFilesService filesService;

    LoggerFilesController filesController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.filesController = new LoggerFilesController(filesService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.filesController).build();
    }

    @Test
    public void getAuditFile() throws Exception {
        File file = new File("logs/Audit.log");

        when(filesService.getAuditLogFile()).thenReturn(file);

        MvcResult result = this.mockMvc.perform(get(API_PATH + "/audit"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("Content-Disposition", "attachment; filename=" + file.getName()))
                .andExpect(header().string("Content-Type", "application/octet-stream"))
                .andReturn();

        int contentLength = result.getResponse().getContentLength();

        verify(filesService, times(1)).getAuditLogFile();
        assertEquals(file.length(), contentLength);
    }

    @Test
    public void getApplicationFile() throws Exception {
        File file = new File("logs/Application.log");

        when(filesService.getApplicationLogFile()).thenReturn(file);

        MvcResult result = this.mockMvc.perform(get(API_PATH + "/application"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("Content-Disposition", "attachment; filename=" + file.getName()))
                .andExpect(header().string("Content-Type", "application/octet-stream"))
                .andReturn();

        int contentLength = result.getResponse().getContentLength();

        verify(filesService, times(1)).getApplicationLogFile();
        assertEquals(file.length(), contentLength);
    }
}