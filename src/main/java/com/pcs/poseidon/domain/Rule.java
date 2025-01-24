package com.pcs.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Rules are preprogrammed orders.
 *
 * Fields:
 * - id: The unique identifier of the rule.
 * - name: The name of the rule.
 * - description: A description of the rule.
 * - json: A JSON formatted string containing additional data about the rule.
 * - template: The template associated with the rule.
 * - sqlStr: The SQL string used by the rule.
 * - sqlPart: A SQL fragment used as part of the rule.
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
