package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.respository.ChangeLogRepository;
import com.spring.springPropertiesEditor.service.PropertiesManager;
import com.spring.springPropertiesEditor.model.Property;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Controller
@ControllerAdvice
public class PropertiesController {

    private PropertiesManager propertiesManager;
    private ChangeLogRepository changeLogRepository;

    public PropertiesController(PropertiesManager propertiesManager, ChangeLogRepository changeLogRepository) {
        this.propertiesManager = propertiesManager;
        this.changeLogRepository = changeLogRepository;
    }

    @GetMapping("/properties")
    String getProperties(@RequestParam(value = "key", required = false) String key, Model model) {
        this.setupModel(model, this.getProperty(key));
        return "properties";
    }

    @PostMapping("/properties")
    public String changeProperty(@ModelAttribute("property") Property property, Model model, BindingResult binding) {
        if (!binding.hasErrors()) {
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

    @GetMapping("/remove")
    public String removeProperty(@RequestParam(value = "key", required = true) @NotNull String key, @RequestParam(value = "value", required = true) @NotNull String value, Model model) {
        this.propertiesManager.removeProperty(key, value);
        this.setupModel(model, new Property());
        return "properties";
    }

    @GetMapping("/clear")
    public String clearInputs(Model model) {
        this.setupModel(model, new Property());
        return "properties";
    }

    private void setupModel(Model model, Property inputProperty) {
        model.addAttribute("property", inputProperty);
        model.addAttribute("properties", this.propertiesManager.getSortedPropertiesList());
        model.addAttribute("changes", this.changeLogRepository.findAll());
    }

    private Property getProperty(String key) {
        return (key != null) ? new Property(key, this.propertiesManager.getProperties().getProperty(key)) : new Property();
    }
}
