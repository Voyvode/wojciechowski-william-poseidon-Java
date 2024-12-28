package com.pcs.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
@Data
public class Trade {

	@Id
	Long id;
	String account;
	String type;
	Double buyQuantity;
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
	LocalDateTime creationDate;
	String revisionName;
	LocalDateTime revisionDate;
	String dealName;
	String dealType;
	String sourceListId;
	String side;

}
