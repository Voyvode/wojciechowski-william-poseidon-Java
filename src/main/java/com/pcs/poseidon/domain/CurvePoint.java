package com.pcs.poseidon.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

/**
 * Represents a point on a curve illustrating the value progression of a financial product.
 */
@Entity
@Table(name = "curve_points")
@Data
public class CurvePoint {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	Long curveId;

	LocalDateTime asOfDate;

	@NotNull
	@Positive
	Double term;

	@Column(name = "\"value\"")
	Double value;

	LocalDateTime creationDate;

}
