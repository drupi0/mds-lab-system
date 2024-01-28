package com.gnoxmacroscode.mdsportalapi.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Template")
public class Template {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String name;

	@Nullable
	private TemplateType type;

    @OneToMany(
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<Group> group = new ArrayList<>();

	@Nullable
	@Column(name = "value", columnDefinition="text")
	private String value;
}
