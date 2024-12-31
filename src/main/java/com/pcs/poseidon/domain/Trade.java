package com.pcs.poseidon.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

/**
 * Represents a financial trade entity.
 * <p>
 * This class maps to the "trades" table in the database and contains information
 * regarding a specific trade, including its account, type, buy quantity, and timestamps
 * for creation and revision dates. The fields `buyQuantity`, `creationDate`, and
 * `revisionDate` are validated to enforce business rules such as mandatory values
 * and positive quantities.
 * <p>
 * Additional unused data fields are left as comments and are not currently utilized in the application.
 */
@Entity
@Table(name = "trades")
@Data
public class Trade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@NotBlank(message = "Account is mandatory")
	String account;

	@NotBlank(message = "Type is mandatory")
	String type;

	@NotNull(message = "Buy quantity is mandatory")
	@Positive(message = "Buy quantity must be positive")
	Double buyQuantity;

	LocalDateTime creationDate;

	LocalDateTime revisionDate;

	/* Specified but unused data */
	Double sellQuantity;
	Double buyPrice;
	Double sellPrice;
	String benchmark;
	LocalDateTime tradeDate;
	String security;
	String status;
	String trader;
	String book;
	String creationName;
	String revisionName;
	String dealName;
	String dealType;
	String sourceListId;
	String side;

}
