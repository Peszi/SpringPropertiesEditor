package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.PropertiesManager;
import com.spring.springPropertiesEditor.model.Property;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Controller
@ControllerAdvice
public class PropertiesController {

    private PropertiesManager propertiesManager;

    public PropertiesController(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    @GetMapping("/")
    String redirect() {
        return "redirect:/properties";
    }

    @GetMapping("/properties")
    String getProperties(@RequestParam(required = false) String key, @RequestParam(required = false) @NotNull String message, Model model) {
        this.setupModel(model, (this.propertiesManager.isLoaded()) ? this.getProperty(key) : new Property());
        model.addAttribute("message", message);
        return "properties";
    }

    @PostMapping("/properties")
    public String changeProperty(@ModelAttribute("property") Property property, BindingResult binding, Model model) {
        if (!binding.hasErrors() && property.getKey().length() > 0) {
            if (this.propertiesManager.hasKey(property.getKey())) {
                this.propertiesManager.editProperty(property.getKey(), property.getValue());
                property.cleanUp();
            } else {
                this.propertiesManager.addProperty(property.getKey(), property.getValue());
                property.cleanUp();
            }
        } else {
            model.addAttribute("message", "cannot change entry!");
        }
        this.setupModel(model, property);
        return "properties";
    }

    @PostMapping("/delete")
    public String removeProperty(@RequestParam Map<String,String> propertyParams, Model model) {
        if (propertyParams.size() == 1) {
            final Map.Entry<String,String> property = propertyParams.entrySet().iterator().next();
            this.propertiesManager.removeProperty(property.getKey(), property.getValue());
        } else {
            model.addAttribute("message", "cannot remove entry!");
        }
        this.setupModel(model, new Property());
        return "properties";
    }

    @ModelAttribute
    public void setupModel(Model model, Property inputProperty) {
        model.addAttribute("property", inputProperty);
        model.addAttribute("properties", this.propertiesManager.getSortedPropertiesList());
    }

    private Property getProperty(String key) {
        return (key != null) ? new Property(key, this.propertiesManager.getProperties().getProperty(key)) : new Property();
    }
}
