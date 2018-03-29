package com.spring.springPropertiesEditor;

import com.spring.springPropertiesEditor.model.UsersProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestPropertiesApplication implements CommandLineRunner {

    private UsersProperties usersProperties;

    public TestPropertiesApplication(UsersProperties usersProperties) {
        this.usersProperties = usersProperties;
    }

    @Override
    public void run(String... args) {
        this.usersProperties.reloadUsers();
    }
}