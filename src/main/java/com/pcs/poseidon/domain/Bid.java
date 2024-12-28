package com.pcs.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "bids")
@Data
public class Bid {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Long id;
	@NotBlank(message = "Account is mandatory")
	String account;
	String type;
	Double bidQuantity;
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
	LocalDateTime creationDate;
	String revisionName;
	LocalDateTime revisionDate;
	String dealName;
	String dealType;
	String sourceListId;
	String side;

}
