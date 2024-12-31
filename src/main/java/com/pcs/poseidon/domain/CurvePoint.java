package com.pcs.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "curve_points")
@Data
public class CurvePoint {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Long id;

	Long curveId;

	LocalDateTime asOfDate;

	@NotNull
	@Positive
	Double term;

	Double value;

	LocalDateTime creationDate;

}
