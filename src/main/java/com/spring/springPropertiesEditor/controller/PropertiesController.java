package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.configuration.PropertiesManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.*;

@RestController
@RequestMapping("/properties")
public class PropertiesController {

    private PropertiesManager propertiesManager;

    public PropertiesController(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    @RequestMapping()
    Map<String, String> getProperties() {
        final TreeMap<String, String> props = this.propertiesManager.printProperties();
        Map<String, String> propsMap = new HashMap<>();
        for (String key : props.descendingKeySet())
            propsMap.put(key, props.get(key));
        return propsMap;
    }

    @RequestMapping("/get")
    Hashtable<Object, Object> get() {
        return this.propertiesManager.getProperties();
    }

    @RequestMapping("/json")
    String getJson() {
//        this.propertiesManager.s();
        return "ok";
    }

    @RequestMapping("/set")
    String setProperty(@PathParam("key") String key, @PathParam("value") String value) {
        this.propertiesManager.setProperty(key, value);
        return "OK";
    }

    @RequestMapping("/read")
    String reloadProperties() {
        this.propertiesManager.readPropertiesFromFile();
        return "OK";
    }

    @RequestMapping("/save")
    String storeProperties() {
        this.propertiesManager.savePropertiesAsProperties();
        return "OK";
    }
}
