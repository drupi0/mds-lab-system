package com.gnoxmacroscode.mdsportalapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "Staff")
public class Staff {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String name;
	private String licNo;
}
