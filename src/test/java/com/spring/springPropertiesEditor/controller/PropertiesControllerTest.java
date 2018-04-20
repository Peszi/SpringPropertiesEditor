package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.model.Property;
import com.spring.springPropertiesEditor.service.ManagePropertiesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class PropertiesControllerTest {

    private static final String API_PATH = "/properties";
    public static final String SOME_KEY = "someKey";
    public static final String SOME_VALUE = "someValue";

    @Mock
    ManagePropertiesService managePropertiesService;

    PropertiesController propertiesController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.propertiesController = new PropertiesController(this.managePropertiesService);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        this.mockMvc = MockMvcBuilders.standaloneSetup(propertiesController)
                .setViewResolvers(viewResolver).build();
    }

    @Test
    public void changeOrAddProperty() throws Exception {
        // Given
        Property property = new Property(SOME_KEY, SOME_VALUE);

        when(managePropertiesService.addOrChangeProperty(any(Property.class))).thenReturn(true);

        // When
        this.mockMvc.perform(post(API_PATH).flashAttr("property", property))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeDoesNotExist("message"))
                .andExpect(view().name("redirect:/properties"));

        // Then
        verify(managePropertiesService, times(1)).addOrChangeProperty(any(Property.class));
    }

    @Test
    public void removeProperty() throws Exception {
        // Given
        MultiValueMap<String, String> properties = new HttpHeaders();
        properties.add(SOME_KEY, SOME_VALUE);

        when(managePropertiesService.removeProperty(any(Property.class))).thenReturn(true);

        // When
        this.mockMvc.perform(post(API_PATH + "/delete").params(properties))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeDoesNotExist("message"))
                .andExpect(view().name("redirect:/properties"));

        // Then
        verify(managePropertiesService, times(1)).removeProperty(any(Property.class));
    }

    @Test
    public void removePropertyFail() throws Exception {
        // Given
        when(managePropertiesService.removeProperty(any(Property.class))).thenReturn(true);

        // When
        this.mockMvc.perform(post(API_PATH + "/delete").params(new HttpHeaders()))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("redirect:/properties"));

        // Then
        verify(managePropertiesService, never()).removeProperty(any(Property.class));
    }

    @Test
    public void getProperties() throws Exception {
        // Given
        Property property = new Property(SOME_KEY, SOME_VALUE);
        when(managePropertiesService.getProperty(anyString())).thenReturn(property);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        // When
        this.mockMvc.perform(get(API_PATH).param("key", SOME_KEY).param("message", "someMessage"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attributeExists("property"))
                .andExpect(model().attributeExists("properties"))
                .andExpect(model().attributeExists("changes"));

        // Then
        verify(managePropertiesService, times(1)).getProperty(stringArgumentCaptor.capture());
        verify(managePropertiesService, times(1)).getAllProperties();
        verify(managePropertiesService, times(1)).getAllAuditLogs();
        assertEquals(SOME_KEY, stringArgumentCaptor.getValue());
    }

    @Test
    public void getPropertiesWoMessage() throws Exception {
        // Given
        Property property = new Property(SOME_KEY, SOME_VALUE);
        when(managePropertiesService.getProperty(anyString())).thenReturn(property);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        // When
        this.mockMvc.perform(get(API_PATH).param("key", SOME_KEY))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeDoesNotExist("message"))
                .andExpect(model().attributeExists("property"))
                .andExpect(model().attributeExists("properties"))
                .andExpect(model().attributeExists("changes"));

        // Then
        verify(managePropertiesService, times(1)).getProperty(stringArgumentCaptor.capture());
        verify(managePropertiesService, times(1)).getAllProperties();
        verify(managePropertiesService, times(1)).getAllAuditLogs();
        assertEquals(SOME_KEY, stringArgumentCaptor.getValue());
    }

    @Test
    public void getPropertiesWoKey() throws Exception {
        // Given
        Property property = new Property(SOME_KEY, SOME_VALUE);
        when(managePropertiesService.getProperty(anyString())).thenReturn(property);

        // When
        this.mockMvc.perform(get(API_PATH))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeDoesNotExist("message"))
                .andExpect(model().attributeDoesNotExist("property"))
                .andExpect(model().attributeExists("properties"))
                .andExpect(model().attributeExists("changes"));

        // Then
        verify(managePropertiesService, never()).getProperty(anyString());
        verify(managePropertiesService, times(1)).getAllProperties();
        verify(managePropertiesService, times(1)).getAllAuditLogs();
    }
}