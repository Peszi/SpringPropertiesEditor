package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.model.User;
import com.spring.springPropertiesEditor.model.UsersProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {

    private UsersProperties usersProperties;

    public UsersController(UsersProperties usersProperties) {
        this.usersProperties = usersProperties;
    }

    @RequestMapping("/reload")
    String reloadAllUsers() {
        this.usersProperties.reloadUsers();
        return "OK";
    }

    @RequestMapping("/all")
    List<User> getAllUsers() {
        return this.usersProperties.getUsers();
    }


//    @Value("${user}")
//    private User[] usersList;

//    @RequestMapping("/all")
//    List<User> getAllUsers() {
//        return new ArrayList<>(Arrays.asList(this.usersList));
//    }

}
