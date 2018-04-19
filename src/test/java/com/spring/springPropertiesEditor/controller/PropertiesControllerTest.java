package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.model.Property;
import com.spring.springPropertiesEditor.service.ManagePropertiesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PropertiesControllerTest {

    private static final String API_PATH = "/properties";

    @Mock
    ManagePropertiesService managePropertiesService;

    @Mock
    Model model;

    @Mock
    BindingResult result;

    PropertiesController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.controller = new PropertiesController(this.managePropertiesService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
    }

    @Test
    public void changeOrAddProperty() throws Exception {

        Property property = new Property();
        property.setKey("someKey");
        property.setValue("someValue");

        ArgumentCaptor<Property> propertyArgumentCaptor = ArgumentCaptor.forClass(Property.class);

        String viewName = this.controller.changeOrAddProperty(property, result, model);

        assertEquals("redirect:/properties", viewName);
        verify(managePropertiesService, times(1)).addOrChangeProperty(any(Property.class));
        verify(model, never()).addAttribute(eq("message"));
        verify(model, times(1)).addAttribute(eq("property"), propertyArgumentCaptor.capture());
        verify(model, times(1)).addAttribute(eq("properties"), any());
        verify(model, times(1)).addAttribute(eq("changes"), any());
        assertEquals("", propertyArgumentCaptor.getValue().getKey());
    }

    @Test
    public void removeProperty() {

        Property property = new Property(); property.setKey("someKey"); property.setValue("someValue");
        Map<String, String> paramMap = new HashMap<>(); paramMap.put(property.getKey(), property.getValue());

        ArgumentCaptor<Property> propertyArgumentCaptor = ArgumentCaptor.forClass(Property.class);

        String viewName = this.controller.removeProperty(paramMap, model);

        assertEquals("redirect:/properties", viewName);
        verify(managePropertiesService, times(1)).removeProperty(propertyArgumentCaptor.capture());
        verify(model, never()).addAttribute(eq("message"));
        verify(model, times(1)).addAttribute(eq("property"), any());
        verify(model, times(1)).addAttribute(eq("properties"), any());
        verify(model, times(1)).addAttribute(eq("changes"), any());
        assertEquals(property.getKey(), propertyArgumentCaptor.getValue().getKey());
        assertEquals(property.getValue(), propertyArgumentCaptor.getValue().getValue());
    }

    @Test
    public void removePropertyFail() {

        Property property = new Property(); property.setKey("someKey"); property.setValue("someValue");

        String viewName = this.controller.removeProperty(new HashMap<>(), model);

        assertEquals("redirect:/properties", viewName);
        verify(managePropertiesService, never()).removeProperty(any());
        verify(model, times(1)).addAttribute(eq("message"), anyString());
    }

    @Test
    public void getProperties() {
        String viewName = this.controller.getProperties("", "", model);
        assertEquals("properties", viewName);
        verify(managePropertiesService, times(1)).getAllProperties();
        verify(model, times(1)).addAttribute(eq("message"), anyString());
    }
}