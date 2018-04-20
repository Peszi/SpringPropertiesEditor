package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.Properties;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.verify;

public class PropertiesServiceImplTest {

    private static final Property SOME_PROPERTY_A = new Property("some.keyA", "some.valueA");
    private static final Property SOME_PROPERTY_B = new Property("some.keyB", "some.valueB");
    private static final Property SOME_PROPERTY_C = new Property("some.keyC", "some.valueC");

    private static final String SOME_PROPERTIES = SOME_PROPERTY_A.getKey() + "=" + SOME_PROPERTY_A.getValue() + "\n" +
                                                    SOME_PROPERTY_B.getKey() + "=" + SOME_PROPERTY_B.getValue();

    @InjectMocks
    Properties properties;

    PropertiesServiceImpl propertiesService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.propertiesService = new PropertiesServiceImpl(properties);
    }

    @Test
    public void loadProperties() throws IOException {
        // Given
        ByteArrayInputStream inputStream = new ByteArrayInputStream(SOME_PROPERTIES.getBytes());
        // When
        boolean loadProperties = this.propertiesService.loadProperties(inputStream);
        // Then
        assertTrue(loadProperties);
        assertEquals(2, this.propertiesService.getProperties().size());
    }

    @Test
    public void addProperty() {
        // Given
        this.propertiesService.loadProperties(new ByteArrayInputStream(SOME_PROPERTIES.getBytes()));
        this.propertiesService.addProperty(SOME_PROPERTY_C);
        // When
        String resultValue = this.propertiesService.getProperties().getProperty(SOME_PROPERTY_C.getKey());
        // Then
        assertEquals(3, this.propertiesService.getProperties().size());
        assertEquals(SOME_PROPERTY_C.getValue(), resultValue);
    }

    @Test
    public void editProperty() {
        final String NEW_VALUE = "newValue";
        // Given
        this.propertiesService.loadProperties(new ByteArrayInputStream(SOME_PROPERTIES.getBytes()));
        this.propertiesService.editProperty(new Property(SOME_PROPERTY_A.getKey(), NEW_VALUE));
        // When
        String resultValue = this.propertiesService.getProperties().getProperty(SOME_PROPERTY_A.getKey());
        // Then
        assertEquals(2, this.propertiesService.getProperties().size());
        assertEquals(NEW_VALUE, resultValue);
    }

    @Test
    public void removeProperty() {
        // Given
        this.propertiesService.loadProperties(new ByteArrayInputStream(SOME_PROPERTIES.getBytes()));
        this.propertiesService.removeProperty(SOME_PROPERTY_A);
        // When
        String resultValue = this.propertiesService.getProperties().getProperty(SOME_PROPERTY_A.getKey());
        // Then
        assertEquals(1, this.propertiesService.getProperties().size());
        assertNull(resultValue);
    }

    @Test
    public void hasKey() {
        // Given
        this.propertiesService.loadProperties(new ByteArrayInputStream(SOME_PROPERTIES.getBytes()));
        // When
        boolean result = this.propertiesService.hasKey(SOME_PROPERTY_A.getKey());
        // Then
        assertTrue(result);
    }

    @Test
    public void getProperties() {
        // Given
        ByteArrayInputStream inputStream = new ByteArrayInputStream(SOME_PROPERTIES.getBytes());
        this.propertiesService.loadProperties(inputStream);
        // When
        propertiesService.addProperty(SOME_PROPERTY_C);
        // Then
        assertEquals(3, this.propertiesService.getProperties().size());
    }

    @Test
    public void saveProperties() {
        // Given
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.propertiesService.loadProperties(new ByteArrayInputStream(SOME_PROPERTIES.getBytes()));
        // When
        boolean result = this.propertiesService.saveProperties(outputStream);
        // Then
        assertTrue(result);
        assertThat(new String(outputStream.toByteArray()), containsString(SOME_PROPERTY_A.getKey() + "=" + SOME_PROPERTY_A.getValue()));
        assertThat(new String(outputStream.toByteArray()), containsString(SOME_PROPERTY_B.getKey() + "=" + SOME_PROPERTY_B.getValue()));
    }
}