package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.model.Property;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PropertiesServiceImplTest {

    @Mock
    Properties properties;

    @InjectMocks
    PropertiesServiceImpl propertiesService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void editProperty() {

        Property property = new Property("some.key", "some.value");
        final String oldValue1 = "some.oldValue";

        when(properties.getProperty(anyString())).thenReturn(oldValue1);
        when(properties.replace(anyString(), anyString(), anyString())).thenReturn(true);

        Optional<String> oldValue = this.propertiesService.editProperty(property);

        verify(properties, times(1)).getProperty(anyString());
        verify(properties, times(1)).replace(anyString(), anyString(), anyString());
        assertEquals(oldValue1, oldValue.get());

    }
}