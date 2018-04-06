package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.LogsFileService;
import com.spring.springPropertiesEditor.service.PropertiesManagerService;
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

    private PropertiesManagerService propertiesManagerService;
    private LogsFileService logsFileService;

    public PropertiesController(PropertiesManagerService propertiesManagerService, LogsFileService logsFileService) {
        this.propertiesManagerService = propertiesManagerService;
        this.logsFileService = logsFileService;
    }

    @GetMapping("/")
    String redirect() {
        return "redirect:/properties";
    }

    @GetMapping("/properties")
    String getProperties(@RequestParam(required = false) String key, @RequestParam(required = false) @NotNull String message, Model model) {
        this.setupModel(model, (this.propertiesManagerService.areLoaded()) ? this.getProperty(key) : new Property());
        model.addAttribute("message", message);
        return "properties";
    }

    @PostMapping("/properties")
    public String changeProperty(@ModelAttribute("property") Property property, BindingResult binding, Model model) {
        if (!binding.hasErrors() && property.getKey().length() > 0) {
            this.propertiesManagerService.modifyProperty(property.getKey(), property.getValue());
            property.cleanUp();
        } else {
            model.addAttribute("message", "cannot change entry!");
        }
        this.setupModel(model, property);
        return "redirect:/properties";
    }

    @PostMapping("/delete")
    public String removeProperty(@RequestParam Map<String,String> propertyParams, Model model) {
        if (propertyParams.size() == 1) {
            final Map.Entry<String,String> property = propertyParams.entrySet().iterator().next();
            this.propertiesManagerService.removeProperty(property.getKey(), property.getValue());
        } else {
            model.addAttribute("message", "cannot remove entry!");
        }
        this.setupModel(model, new Property());
        return "redirect:/properties";
    }

    @ModelAttribute
    public void setupModel(Model model, Property inputProperty) {
        model.addAttribute("property", inputProperty);
        model.addAttribute("properties", this.propertiesManagerService.getSortedPropertiesList());
        model.addAttribute("changes", this.propertiesManagerService.getAuditLogList());
    }

    private Property getProperty(String key) {
        return (key != null) ? new Property(key, this.propertiesManagerService.getProperties().getProperty(key)) : new Property();
    }
}
