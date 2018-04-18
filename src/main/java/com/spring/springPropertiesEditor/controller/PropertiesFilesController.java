package com.spring.springPropertiesEditor.controller;

import com.spring.springPropertiesEditor.service.PropertiesFileService;
import com.spring.springPropertiesEditor.service.PropertiesFileServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/properties")
public class PropertiesFilesController {

    private static final String FILE_FAIL_MESSAGE = "Properties are not loaded yet!";

    private PropertiesFileService fileService;

    public PropertiesFilesController(PropertiesFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    String uploadFile(@ModelAttribute("file") MultipartFile file, Model model) {
        if (!this.fileService.loadPropertiesFile(file))
            model.addAttribute("message", "cannot load this file!");
        return "redirect:/properties"; // TODO
    }

    @GetMapping("/download")
    void downloadPropertiesFile(HttpServletResponse response) {
        try {
            if (this.fileService.isFileLoaded()) {
                response.addHeader("Content-Disposition", "attachment; filename=" + this.fileService.getFileName());
                response.setContentType("application/octet-stream");
                this.fileService.getPropertiesStream(response.getOutputStream());
                response.flushBuffer();
            } else {
                response.sendRedirect("/properties?message=" + PropertiesFilesController.FILE_FAIL_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/download/json")
    void downloadJsonAudit(HttpServletResponse response) {
        try {
            if (this.fileService.isFileLoaded()) {
                response.addHeader("Content-Disposition", "attachment; filename=" + this.fileService.getFileName()); // TODO
                response.setContentType("application/octet-stream");
                this.fileService.getJsonStream(response.getOutputStream());
                response.flushBuffer();
            } else {
                response.sendRedirect("/properties?message=" + PropertiesFilesController.FILE_FAIL_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/download/yaml")
    void downloadYamlAudit(HttpServletResponse response) {
        try {
            if (this.fileService.isFileLoaded()) {
                response.addHeader("Content-Disposition", "attachment; filename=" + this.fileService.getFileName()); // TODO
                response.setContentType("application/octet-stream");
                this.fileService.getYamlStream(response.getOutputStream());
                response.flushBuffer();
            } else {
                response.sendRedirect("/properties?message=" + PropertiesFilesController.FILE_FAIL_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
