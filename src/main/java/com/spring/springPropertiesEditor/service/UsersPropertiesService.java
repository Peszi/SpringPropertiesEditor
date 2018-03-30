package com.spring.springPropertiesEditor.service;

import com.spring.springPropertiesEditor.configuration.PropertiesManager;
import com.spring.springPropertiesEditor.configuration.UsersProperties;
import com.spring.springPropertiesEditor.controller.UsersController;
import com.spring.springPropertiesEditor.model.User;
import com.spring.springPropertiesEditor.respository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentManager;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class UsersPropertiesService {

    private static final String USER_PROPERTY_TEMPLATE = "user.users[%d].";
    private static final String USER_NAME_SUFIX = "name";
    private static final String USER_SURNAME_SUFIX = "surname";

    final static Logger logger = LoggerFactory.getLogger(UsersPropertiesService.class);

    @Autowired
    private Environment environment;

    private ApplicationContext applicationContext;

    private UsersRepository usersRepository;
    private UsersProperties usersProperties;

    public UsersPropertiesService(ApplicationContext applicationContext, UsersRepository usersRepository, UsersProperties usersProperties) {
        this.applicationContext = applicationContext;
        this.usersRepository = usersRepository;
        this.usersProperties = usersProperties;
    }

    @PostConstruct
    public void init() {
        this.loadUsersPropertiesData();
    }

    public void changeUsername(long idx, String username) {
        this.setUserProperty(String.format(USER_PROPERTY_TEMPLATE, idx) + USER_NAME_SUFIX, username);
        this.reloadUsersProperties();
    }

    public void changeUserSurname(int idx) {

    }

    public Iterable<User> getAllUsers() {
        this.loadUsersPropertiesData();
        return this.usersRepository.findAll();
    }

    private void setUserProperty(String key, String value) {
        logger.info("changing property - " + key + " : " + value);
        EnvironmentManager environmentManager = this.applicationContext.getBean(EnvironmentManager.class);
        if (!value.equals(environment.getProperty(key))) {
            logger.info("property should be in!!! old: " + environment.getProperty(key) + " to " + value);
        }
        environmentManager.setProperty(key, value);
        this.reloadUsersProperties();
        this.getUserProperty(key);
//        PropertiesManager.getAllProperties(this.environment, logger);
        this.getUserProperty(key);
    }

    public String getUserProperty(String key) {
        final String value = (String) this.applicationContext.getBean(EnvironmentManager.class).getProperty(key);
        logger.info("property value = " + value);
        return value;
    }

    private void reloadUsersProperties() {
        logger.info("reloading user properties!");
        this.applicationContext.getBean(RefreshScope.class).refreshAll();
    }

    public void loadUsersPropertiesData() {
        logger.info("repo clean up!");
        this.usersRepository.deleteAll();
        logger.info("loading into repo!" + (this.usersProperties.getUsers() != null ? true : false));
        for (UsersProperties.User user : this.usersProperties.getUsers()) {
            logger.info("new user " + user.getName() + user.getSurname());
            this.usersRepository.save(new User(user.getName(), user.getSurname()));
        }
    }

    public void getAllProps() {
//        PropertiesManager.getAllProperties(this.environment, logger);
    }
}
