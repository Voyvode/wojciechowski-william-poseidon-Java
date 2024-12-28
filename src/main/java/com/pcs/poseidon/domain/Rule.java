package com.pcs.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "rules")
@Data
public class Rule {

	@Id
	Long id;
	String name;
	String description;
	String json;
	String template;
	String sqlStr;
	String sqlPart;

}
