package com.pcs.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "ratings")
@Data
public class Rating {

	@Id
	Integer id;
	String moodysRating;
	String sandpRating;
	String fitchRating;
	Integer orderNumber;

}
