package com.spring.springPropertiesEditor.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Properties;

@Configuration
public class BasicConfiguration {

    @Bean("UserProperties")
    public Properties getProperties() {
        return new Properties();
    }

}
