package com.spring.springPropertiesEditor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class BasicConfiguration {

    @Bean("UserProperties")
    public Properties getProperties() {
        return new Properties();
    }

}
