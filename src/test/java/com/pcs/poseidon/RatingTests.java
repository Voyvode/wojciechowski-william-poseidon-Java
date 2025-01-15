package com.pcs.poseidon;

import com.pcs.poseidon.domain.Rating;
import com.pcs.poseidon.repositories.RatingRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

	@Test
	public void ratingTest() {
		var rating = new Rating();
		rating.setMoodysRating("Moody's Rating");
		rating.setSandpRating("s&P Rating");
		rating.setFitchRating("Fitch Rating");
		rating.setOrderNumber(10L);

		// Save
		rating = ratingRepository.save(rating);
		assertNotNull(rating.getId());
		assertEquals(10L, (long) rating.getOrderNumber());

		// Update
		rating.setOrderNumber(20L);
		rating = ratingRepository.save(rating);
		assertEquals(20L, (long) rating.getOrderNumber());

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		assertFalse(listResult.isEmpty());

		// Delete
		var id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		assertFalse(ratingList.isPresent());
	}
}
