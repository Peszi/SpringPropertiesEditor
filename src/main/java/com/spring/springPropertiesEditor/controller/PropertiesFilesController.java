package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.PropertiesFilesService;
import com.spring.springPropertiesEditor.util.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/properties")
public class PropertiesFilesController {

    private PropertiesFilesService fileService;

    public PropertiesFilesController(PropertiesFilesService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute("file") MultipartFile file, Model model) throws FileNotFoundException {
        this.fileService.loadPropertiesFile(file);
        return "redirect:/properties";
    }

    @GetMapping("/download")
    public void downloadPropertiesFile(HttpServletResponse response) throws IOException {
        this.setupResponse(response, FileType.PROPERTIES);
    }

    @GetMapping("/download/json")
    public void downloadJsonAudit(HttpServletResponse response) throws IOException {
        this.setupResponse(response, FileType.JSON);
    }

    @GetMapping("/download/yaml")
    public void downloadYamlAudit(HttpServletResponse response) throws IOException {
        this.setupResponse(response, FileType.YAML);
    }

    private void setupResponse(HttpServletResponse response, FileType fileType) throws IOException {
        response.addHeader("Content-Disposition", "attachment; filename=" + this.fileService.getPropertiesFileName(fileType));
        response.setContentType("application/octet-stream");
        response.getOutputStream().write(this.fileService.getPropertiesByteArray(fileType));
        response.flushBuffer();
    }
}
