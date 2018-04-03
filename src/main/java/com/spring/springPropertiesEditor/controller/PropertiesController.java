package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.configuration.PropertiesManager;
import com.spring.springPropertiesEditor.model.Property;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PropertiesController {

    private PropertiesManager propertiesManager;

    public PropertiesController(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    @GetMapping("/properties")
    String getProperties(Model model) {
        model.addAttribute("property",  new Property());
        model.addAttribute("properties",  this.propertiesManager.getPropertiesList());
        return "properties";
    }

    @PostMapping("/properties")
    public String changeProperty(@ModelAttribute("property") Property property, Model model, BindingResult binding) {
        if (!binding.hasErrors()) {
            System.out.println("K " + property.getKey() + " V " + property.getValue());
            if (this.propertiesManager.isPropertyKeyIn(property.getKey())) {
                this.propertiesManager.editProperty(property.getKey(), property.getValue());
                property.cleanUp();
                model.addAttribute("properties",  this.propertiesManager.getPropertiesList());
                model.addAttribute("message", "property changed!");
                return "properties";
            }
            this.propertiesManager.addProperty(property.getKey(), property.getValue());
            property.cleanUp();
            model.addAttribute("properties",  this.propertiesManager.getPropertiesList());
            model.addAttribute("message", "property added!");
            return "properties";
        }
        model.addAttribute("properties",  this.propertiesManager.getPropertiesList());
        model.addAttribute("message", "cannot change entry!");
        return "properties";
    }

    @PostMapping("/remove")
    public String removeProperty(@ModelAttribute("property") Property property, Model model, BindingResult binding) {
        System.out.println("K " + property.getKey() + " V " + property.getValue());
        if (this.propertiesManager.removeProperty(property.getKey(), property.getValue())) {
            model.addAttribute("message", "removed property!");
        }
        model.addAttribute("properties",  this.propertiesManager.getPropertiesList());
        return "properties";
    }
}
