package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.configuration.PropertiesManager;
import com.spring.springPropertiesEditor.configuration.UsersProperties;
import com.spring.springPropertiesEditor.model.User;
import com.spring.springPropertiesEditor.respository.UsersRepository;
import com.spring.springPropertiesEditor.service.UsersPropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentManager;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.NavigableSet;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersPropertiesService usersPropertiesService;

    public UsersController(UsersPropertiesService usersPropertiesService) {
        this.usersPropertiesService = usersPropertiesService;
    }

    @RequestMapping()
    Iterable<User> getAllUsers() {
        return this.usersPropertiesService.getAllUsers();
    }

    @RequestMapping("/props")
    String getAllProps() {
        this.usersPropertiesService.getAllProps();
        return "ok";
    }

    @RequestMapping("/{userId}/{username}")
    String changeConfig(@PathVariable Long userId, @PathVariable String username) {
        this.usersPropertiesService.changeUsername(userId, username);
        return "ok";
    }
}
