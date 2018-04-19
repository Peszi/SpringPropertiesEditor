package com.spring.springPropertiesEditor.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Properties;

@Slf4j
@Configuration
public class BasicConfiguration {

    @Bean("UserProperties")
    @Scope("singleton")
    public Properties getProperties() {
        log.info("Getting PROPERTIES BEAN!");
        return new Properties();
    }

}
