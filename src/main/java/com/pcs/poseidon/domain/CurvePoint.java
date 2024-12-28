package com.pcs.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "curve_points")
@Data
public class CurvePoint {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Long id;
	@NotNull(message = "Must not be null")
	Long curveId;
	@NotNull
	LocalDateTime asOfDate;
	@NotNull
	@Positive
	Double term;
	Double value;
	Timestamp creationDate;

}
