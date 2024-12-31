package com.pcs.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Bids are financial offers where buyers or sellers set their price.
 * <p>
 * It includes attributes such as account, type, bid quantities, and aggregates other metadata.
 * <p>
 * Additional unused data fields are left as comments and are not currently utilized in the application.
 */
@Entity
@Table(name = "bids")
@Data
public class Bid {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Long id;

	@NotBlank(message = "Account is mandatory")
	String account;

	@NotBlank(message = "Type is mandatory")
	String type;

	@NotNull(message = "Bid quantity is mandatory")
	@Positive(message = "Bid quantity must be positive")
	Double bidQuantity;

	LocalDateTime creationDate;

	LocalDateTime revisionDate;

	/* Specified but unused data */
	Double askQuantity;
	Double bid;
	Double ask;
	String benchmark;
	LocalDateTime bidListDate;
	String commentary;
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
