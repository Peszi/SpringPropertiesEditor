package com.spring.springPropertiesEditor.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RefreshScope
@PropertySource("classpath:/users.properties")
@ConfigurationProperties(prefix = "user")
public class UsersProperties {

    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public static class User {

        private String name;
        private String surname;

        public void setName(String name) {
            this.name = name;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

    }
}
