package com.gnoxmacroscode.mdsportalapi.controller;

import com.gnoxmacroscode.mdsportalapi.model.Template;
import com.gnoxmacroscode.mdsportalapi.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1")
public class TemplateController {

    @Autowired
    private TemplateRepository templateRepo;

    @GetMapping("/template")
    public List<Template> getAllTemplates(){
        return this.templateRepo.findAll();
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @PostMapping("/template")
    public Template saveTemplate(@RequestBody Template template) {
        return this.templateRepo.save(template);
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @DeleteMapping("/template")
    public Template deleteTemplate(@RequestBody Template template) {
        try {
            this.templateRepo.delete(template);
            return template;
        } catch (Exception e) {
            return null;
        }
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @PatchMapping("/template")
    public Template updateTemplate(@RequestBody Template template) throws Exception {
        Optional<Template> updatedTemplate = this.templateRepo.findById(template.getId());

        if(updatedTemplate.isEmpty()) {
            throw new Exception("Template with " + template.getId() + " not found");
        }

        return this.templateRepo.save(template);
    }
}
