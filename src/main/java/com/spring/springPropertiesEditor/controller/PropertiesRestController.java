package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.configuration.PropertiesManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.*;

@RestController
@RequestMapping("/props")
public class PropertiesRestController {

    private PropertiesManager propertiesManager;

    public PropertiesRestController(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }


    @RequestMapping(value = "/html", method = RequestMethod.GET)
    public String getExampleHTML(Model model) {
        model.addAttribute("title", "Baeldung");
        model.addAttribute("description", "<strong>Thymeleaf</strong> tutorial");
        return "index.html";
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
        this.propertiesManager.editProperty(key, value);
        return "OK";
    }

    @RequestMapping("/read")
    String reloadProperties() {
        this.propertiesManager.loadPropertiesFile();
        return "OK";
    }

    @RequestMapping("/save")
    String storeProperties() {
        this.propertiesManager.savePropertiesAsProperties();
        return "OK";
    }

//    final TreeMap<String, String> props = this.propertiesManager.printProperties();
//    Map<String, String> propsMap = new HashMap<>();
//        for (String key : props.descendingKeySet())
//            propsMap.put(key, props.get(key));
}
