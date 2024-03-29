package com.gnoxmacroscode.mdsportalapi.repository;

import com.gnoxmacroscode.mdsportalapi.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TemplateRepository  extends JpaRepository<Template, UUID> {
}
