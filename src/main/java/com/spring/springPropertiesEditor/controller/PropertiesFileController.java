package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.PropertiesManager;
import com.spring.springPropertiesEditor.model.Property;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@ControllerAdvice
public class PropertiesFileController {

    private PropertiesManager propertiesManager;

    public PropertiesFileController(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    @PostMapping("/upload")
    String uploadFile(@ModelAttribute("file") MultipartFile file, Model model) {
        if (!this.propertiesManager.loadPropertiesFile(file))
            model.addAttribute("message", "cannot load this file!");
        model.addAttribute("property", new Property());
        model.addAttribute("properties", this.propertiesManager.getSortedPropertiesList());
        return "properties";
    }

    @GetMapping("/download")
    String downloadFile(HttpServletResponse response, Model model) {
        if (this.propertiesManager.isLoaded()) {
            try {
                response.addHeader("Content-Disposition", "attachment; filename=" + this.propertiesManager.getPropertiesFileName());
                response.setContentType("application/octet-stream");
                this.propertiesManager.getPropertiesOutputStream(response.getOutputStream());
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            model.addAttribute("message", "you need to load file first!");
        }
        model.addAttribute("property", new Property());
        model.addAttribute("properties", this.propertiesManager.getSortedPropertiesList());
        return "properties";
    }
}
