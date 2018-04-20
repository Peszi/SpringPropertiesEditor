package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.exception.BadRequestException;
import com.spring.springPropertiesEditor.model.Property;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ManagePropertiesServiceImplTest {

    @Mock
    AuditLoggerService loggerService;

    @Mock
    PropertiesService propertiesService;

    ManagePropertiesServiceImpl managePropertiesService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.managePropertiesService = new ManagePropertiesServiceImpl(loggerService, propertiesService);
    }

    @Test
    public void addProperty() {
        // Given
        Property property = new Property("someKey", "someValue");
        when(this.propertiesService.hasKey(anyString())).thenReturn(false);
        // When
        boolean result = this.managePropertiesService.addOrChangeProperty(property);
        // Then
        verify(this.propertiesService, times(1)).hasKey(anyString());
        verify(this.propertiesService, times(1)).addProperty(any(Property.class));
        verify(this.propertiesService, never()).editProperty(any(Property.class));
        assertTrue(result);
    }

    @Test
    public void editProperty() {
        // Given
        Property property = new Property("someKey", "someValue");
        when(this.propertiesService.hasKey(anyString())).thenReturn(true);
        when(this.propertiesService.editProperty(any(Property.class))).thenReturn(Optional.of("oldValue"));
        // When
        boolean result = this.managePropertiesService.addOrChangeProperty(property);
        // Then
        verify(this.propertiesService, times(1)).hasKey(anyString());
        verify(this.propertiesService, times(1)).editProperty(any(Property.class));
        verify(this.propertiesService, never()).addProperty(any(Property.class));
        assertTrue(result);
    }

    @Test(expected = BadRequestException.class)
    public void editNotExistingProperty() {
        // Given
        Property property = new Property("someKey", "someValue");
        when(this.propertiesService.hasKey(anyString())).thenReturn(true);
        when(this.propertiesService.editProperty(any(Property.class))).thenReturn(Optional.empty());
        // When
        boolean result = this.managePropertiesService.addOrChangeProperty(property);
        // Then
        verify(this.propertiesService, times(1)).hasKey(anyString());
        verify(this.propertiesService, times(1)).editProperty(any(Property.class));
        verify(this.propertiesService, never()).addProperty(any(Property.class));
        assertTrue(result);
    }

    @Test
    public void removeProperty() {
        Property property = new Property("someKey", "someValue");
        when(this.propertiesService.removeProperty(any(Property.class))).thenReturn(true);
        // When
        boolean result = this.managePropertiesService.removeProperty(property);
        // Then
        verify(this.propertiesService, times(1)).removeProperty(any(Property.class));
        assertTrue(result);
    }

    @Test
    public void getProperty() {
        Property property = new Property("someKey", "someValue");
        Properties properties = new Properties();
        properties.put(property.getKey(), property.getValue());

        when(this.propertiesService.hasKey(anyString())).thenReturn(true);
        when(this.propertiesService.getProperties()).thenReturn(properties);
        // When
        Property propertyRemoved = this.managePropertiesService.getProperty(property.getKey());
        // Then
        verify(this.propertiesService, times(1)).getProperties();
        assertEquals(property.getValue(), propertyRemoved.getValue());
    }

    @Test
    public void getAllProperties() {
        Property property = new Property("someKey", "someValue");
        Properties properties = new Properties();
        properties.put(property.getKey(), property.getValue());

        when(this.propertiesService.getProperties()).thenReturn(properties);
        // When
        Map<String, String> allProperties = this.managePropertiesService.getAllProperties();
        // Then
        verify(this.propertiesService, times(2)).getProperties();
        assertEquals(1, allProperties.size());
    }

    @Test
    public void getAllAuditLogs() {
        List<String> auditList = new ArrayList<>();
        auditList.add("audit1!");
        auditList.add("audit2!");

        when(this.loggerService.getAuditLogList(anyBoolean())).thenReturn(auditList);
        // When
        List<String> allAuditLogs = this.managePropertiesService.getAllAuditLogs();
        // Then
        verify(this.loggerService, times(1)).getAuditLogList(anyBoolean());
        assertEquals(allAuditLogs, auditList);
    }
}