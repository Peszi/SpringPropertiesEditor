package com.spring.springPropertiesEditor.restcontroller;

import com.spring.springPropertiesEditor.service.PropertiesFilesService;
import com.spring.springPropertiesEditor.util.FileType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/properties")
public class PropertiesRestController {

    private PropertiesFilesService fileService;

    public PropertiesRestController(PropertiesFilesService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@ModelAttribute("file") MultipartFile file, Model model) throws FileNotFoundException {
        this.fileService.loadPropertiesFile(file);
        return "redirect:/properties";
    }

    @GetMapping("/download")
    public ResponseEntity downloadPropertiesFile() throws IOException {
        return this.setupResponse(FileType.PROPERTIES);
    }

    @GetMapping("/download/json")
    public ResponseEntity downloadJsonAudit() throws IOException {
        return this.setupResponse(FileType.JSON);
    }

    @GetMapping("/download/yaml")
    public ResponseEntity downloadYamlAudit() throws IOException {
        return this.setupResponse(FileType.YAML);
    }

    private ResponseEntity setupResponse(FileType fileType) throws FileNotFoundException {
        final byte[] propertiesArray = this.fileService.getPropertiesByteArray(fileType);
        return ResponseEntity.ok()
                .contentLength(propertiesArray.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header("Content-Disposition", "attachment; filename=" + this.fileService.getPropertiesFileName(fileType))
                .body(propertiesArray);
    }

}
