package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.configuration.PropertiesManager;
import com.spring.springPropertiesEditor.model.Property;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    private PropertiesManager propertiesManager;

    public FileController(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    @GetMapping("/index")
    String getLoader(Model model) {
        return "index";
    }

    @PostMapping("/upload")
    String getPropertiesFile(@ModelAttribute("file") MultipartFile file, Model model) {
        if (this.propertiesManager.loadPropertiesFile(file))
            return "redirect:/properties";
        model.addAttribute("message", "cannot load this file!");
        return "index";
    }
}
