package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.ManagePropertiesService;
import com.spring.springPropertiesEditor.service.ManagePropertiesServiceImpl;
import com.spring.springPropertiesEditor.model.Property;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/properties")
public class PropertiesController {

    private ManagePropertiesService managePropertiesService;

    public PropertiesController(ManagePropertiesService managePropertiesService) {
        this.managePropertiesService = managePropertiesService;
    }

    @PostMapping()
    public String changeOrAddProperty(@ModelAttribute Property property, BindingResult binding, Model model) {
        if (!binding.hasErrors()) {
            this.managePropertiesService.addOrChangeProperty(property);
            property.cleanUp();
        } else {
            model.addAttribute("message", "cannot change entry!");
        }
        this.setupModel(model, property);
        return "redirect:/properties";
    }

    @GetMapping()
    public String getProperties(@RequestParam(required = false) String key, @RequestParam(required = false) String message, Model model) {
        this.setupModel(model, new Property()); // TODO
        model.addAttribute("message", message);
        return "properties";
    }

    @PostMapping("/delete")
    public String removeProperty(@ModelAttribute Property property, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            this.managePropertiesService.removeProperty(property);
        } else {
            model.addAttribute("message", "cannot remove entry!");
        }
        this.setupModel(model, new Property());
        return "redirect:/properties";
    }

    @ModelAttribute
    public void setupModel(Model model, Property inputProperty) {
        model.addAttribute("property", inputProperty);
        model.addAttribute("properties", this.managePropertiesService.getAllProperties());
//        model.addAttribute("changes", this.managePropertiesServiceImpl.getAuditLogList());
    }

//    private Property getProperty(String key) {
//        if (this.managePropertiesServiceImpl.arePropertiesLoaded() || key != null)
//            return new Property(key, this.managePropertiesServiceImpl.getProperties().getProperty(key));
//        return new Property();
//    }
}
