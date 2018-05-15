package com.spring.springPropertiesEditor.restcontroller;

import com.spring.springPropertiesEditor.service.ManagePropertiesService;
import com.spring.springPropertiesEditor.service.PropertiesFilesService;
import com.spring.springPropertiesEditor.util.FileType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/properties")
public class PropertiesFilesRestController {

    private PropertiesFilesService fileService;
    private ManagePropertiesService manageService;

    public PropertiesFilesRestController(PropertiesFilesService fileService, ManagePropertiesService manageService) {
        this.fileService = fileService;
        this.manageService = manageService;
    }

    @CrossOrigin
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestParam MultipartFile file) throws IOException {
        this.fileService.loadPropertiesFile(file);
        return ResponseEntity.ok(this.manageService.getAllProperties());
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity getPropertiesFile() throws FileNotFoundException {
        return this.setupResponse(FileType.PROPERTIES);
    }

    @GetMapping("/download/json")
    public ResponseEntity getJsonFile() throws FileNotFoundException {
        return this.setupResponse(FileType.JSON);
    }

    @GetMapping("/download/yaml")
    public ResponseEntity getYamlFile() throws FileNotFoundException {
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
