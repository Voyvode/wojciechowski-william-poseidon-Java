package com.pcs.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Rules are automated orders triggered by specific monitored values or market behavior.
 */
@Entity
@Table(name = "rules")
@Data
public class Rule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@NotBlank(message = "Name is mandatory")
	String name;

	String description;

	String json;

	String template;

	String sqlStr;

	String sqlPart;

}
