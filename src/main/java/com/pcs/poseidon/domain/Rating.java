package com.pcs.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * Credit rating information from several agencies about the financial entity related to a given order.
 */
@Entity
@Table(name = "ratings")
@Data
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String moodysRating;

	String sandpRating;

	String fitchRating;

	@NotNull(message = "Order number is mandatory")
	Long orderNumber;

}
