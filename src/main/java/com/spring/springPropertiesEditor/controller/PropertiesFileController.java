package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.PropertiesManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PropertiesFileController {

    private static final String FILE_FAIL_MESSAGE = "Properties are not loaded yet!";

    private PropertiesManager propertiesManager;

    public PropertiesFileController(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    @PostMapping("/properties/upload")
    String uploadFile(@ModelAttribute("file") MultipartFile file, Model model) {
        if (!this.propertiesManager.loadPropertiesFile(file))
            model.addAttribute("message", "cannot load this file!");
        return "redirect:/properties"; // TODO
    }

    @GetMapping("/properties/download")
    void downloadPropertiesFile(HttpServletResponse response) {
        try {
            if (this.propertiesManager.isLoaded()) {
                response.addHeader("Content-Disposition", "attachment; filename=" + this.propertiesManager.getPropertiesFileName());
                response.setContentType("application/octet-stream");
                this.propertiesManager.getPropertiesOutputStream(response.getOutputStream());
                response.flushBuffer();
            } else {
                response.sendRedirect("/properties?message=" + PropertiesFileController.FILE_FAIL_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
