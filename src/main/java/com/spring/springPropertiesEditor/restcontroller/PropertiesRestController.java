package com.spring.springPropertiesEditor.restcontroller;

import com.spring.springPropertiesEditor.exception.BadRequestException;
import com.spring.springPropertiesEditor.model.Property;
import com.spring.springPropertiesEditor.service.ManagePropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/properties")
public class PropertiesRestController {

    private ManagePropertiesService managePropertiesService;

    public PropertiesRestController(ManagePropertiesService managePropertiesService) {
        this.managePropertiesService = managePropertiesService;
    }

    @PostMapping
    public ResponseEntity changeOrAddProperty(@Valid @ModelAttribute Property property, BindingResult binding) {
        if (!binding.hasErrors()) {
            this.managePropertiesService.addOrChangeProperty(property);
            return ResponseEntity.ok().build();
        }
        throw new BadRequestException("Incorrect property!");
    }

    @DeleteMapping
    public ResponseEntity removeProperty(@Valid @ModelAttribute Property property, BindingResult binding) {
        if (!binding.hasErrors()) {
            this.managePropertiesService.removeProperty(property);
            return ResponseEntity.ok().build();
        }
        throw new BadRequestException("Incorrect property!");
    }

    @GetMapping
    public ResponseEntity getProperties() {
        return ResponseEntity.ok(this.managePropertiesService.getAllProperties());
    }

    @GetMapping("/audit")
    public ResponseEntity getPropertiesAuditLogs() {
        return ResponseEntity.ok(this.managePropertiesService.getAllAuditLogs());
    }
}
