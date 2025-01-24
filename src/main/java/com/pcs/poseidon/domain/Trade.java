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
 * An operation performed on a financial product.
 * <p>
 * Note: Additional unused data fields are intentionally left empty
 * as they are not currently utilized in the application.
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
