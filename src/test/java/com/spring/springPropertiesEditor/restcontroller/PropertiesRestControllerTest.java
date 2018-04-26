package com.spring.springPropertiesEditor.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.springPropertiesEditor.exception.BadRequestException;
import com.spring.springPropertiesEditor.model.Property;
import com.spring.springPropertiesEditor.service.ManagePropertiesService;
import com.spring.springPropertiesEditor.service.PropertiesFilesService;
import com.spring.springPropertiesEditor.util.FileType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PropertiesRestControllerTest {

    private static final String API_PATH = "/api/properties";

    @Mock
    ManagePropertiesService managePropertiesService;

    PropertiesRestController restController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.restController = new PropertiesRestController(this.managePropertiesService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.restController).build();
    }

    @Test
    public void changeOrAddProperty() throws Exception {

        Property property = new Property();
        property.setKey("someKey");
        property.setValue("someValue");

        this.mockMvc.perform(post(API_PATH).flashAttr("property", property))
                .andExpect(status().is2xxSuccessful());

        verify(managePropertiesService, times(1)).addOrChangeProperty(any(Property.class));
    }

    @Test
    public void changeOrAddPropertyWithInvalidKey() throws Exception {

        Property property = new Property();
        property.setValue("someValue");

        this.mockMvc.perform(post(API_PATH).flashAttr("property", property))
                .andExpect(status().is4xxClientError());

        verify(managePropertiesService, times(0)).addOrChangeProperty(any(Property.class));
    }

    @Test
    public void removeProperty() throws Exception {

        Property property = new Property("someKey", "someValue");

        this.mockMvc.perform(delete(API_PATH).flashAttr("property", property))
                .andExpect(status().is2xxSuccessful());

        verify(managePropertiesService, times(1)).removeProperty(any(Property.class));
    }

    @Test
    public void removePropertyWithInavlidKey() throws Exception {

        Property property = new Property();
        property.setKey("");
        property.setValue("someValue");

        this.mockMvc.perform(delete(API_PATH).flashAttr("property", property))
                .andExpect(status().is4xxClientError());

        verify(managePropertiesService, times(0)).removeProperty(any(Property.class));
    }

    @Test
    public void getProperties() throws Exception {

        Map<String, String> propertiesMap = new HashMap<>();
        propertiesMap.put("key", "value");
        propertiesMap.put("key2", "value2");

        when(managePropertiesService.getAllProperties()).thenReturn(propertiesMap);

        MvcResult result = this.mockMvc.perform(get(API_PATH))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        verify(managePropertiesService, times(1)).getAllProperties();
        assertEquals(new ObjectMapper().writeValueAsString(propertiesMap), resultContent);
    }

    @Test
    public void getPropertiesAuditLogs() throws Exception {

        List<String> auditList = new ArrayList<>();
        auditList.add("log1");
        auditList.add("log2");

        when(managePropertiesService.getAllAuditLogs()).thenReturn(auditList);

        MvcResult result = this.mockMvc.perform(get(API_PATH + "/audit"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();

        verify(managePropertiesService, times(1)).getAllAuditLogs();
        assertEquals(new ObjectMapper().writeValueAsString(auditList), resultContent);
    }
}