package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.ManagePropertiesService;
import com.spring.springPropertiesEditor.service.ManagePropertiesServiceImpl;
import com.spring.springPropertiesEditor.model.Property;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/properties")
public class PropertiesController {

    private ManagePropertiesService managePropertiesService;

    public PropertiesController(ManagePropertiesService managePropertiesService) {
        this.managePropertiesService = managePropertiesService;
    }

    @PostMapping
    public String changeOrAddProperty(@Valid @ModelAttribute Property property, BindingResult binding, Model model) {
        if (!binding.hasErrors()) {
            this.managePropertiesService.addOrChangeProperty(property);
            property.cleanUp();
        } else {
            model.addAttribute("message", "cannot change entry!");
        }
        this.setupModel(model, property);
        return "redirect:/properties";
    }

    @PostMapping("/delete")
    public String removeProperty(@RequestParam Map<String, String> properties, Model model) {
        if (properties != null && properties.size() == 1) {
            final Map.Entry<String, String> property = properties.entrySet().iterator().next();
            this.managePropertiesService.removeProperty(new Property(property.getKey(), property.getValue()));
        } else {
            model.addAttribute("message", "cannot remove entry!");
        }
        this.setupModel(model, new Property());
        return "redirect:/properties";
    }

    @GetMapping
    public String getProperties(@RequestParam(required = false) String key, @RequestParam(required = false) String message, Model model) {
        this.setupModel(model, this.managePropertiesService.getProperty(key));
        model.addAttribute("message", message);
        return "properties";
    }

    private void setupModel(Model model, Property inputProperty) {
        model.addAttribute("property", inputProperty);
        model.addAttribute("properties", this.managePropertiesService.getAllProperties());
        model.addAttribute("changes", this.managePropertiesService.getAllAuditLogs());
    }
}
