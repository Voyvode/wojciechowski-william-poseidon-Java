package com.pcs.poseidon;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.pcs.poseidon.domain.Rating;
import com.pcs.poseidon.repositories.RatingRepository;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class RatingTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RatingRepository ratingRepository;

	@BeforeEach
	void setup() {
		ratingRepository.deleteAll();
	}

	@Test
	public void testGetRatingList() throws Exception {
		mockMvc.perform(get("/ratings/list")
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Rating List")));
	}

	@Test
	public void testGetAddRatingForm() throws Exception {
		mockMvc.perform(get("/ratings/add")
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(view().name("ratings/add"));
	}

	@Test
	public void testCreateRating() throws Exception {
		mockMvc.perform(post("/ratings/validate")
						.with(user("user").roles("USER"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("moodysRating", "Moody A")
						.param("sandPRating", "S&P A")
						.param("fitchRating", "Fitch A")
						.param("orderNumber", "10"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/ratings/list"));

		List<Rating> ratings = ratingRepository.findAll();
		assertEquals(1, ratings.size(), "Rating wasn't saved in database");
		assertEquals("Moody A", ratings.get(0).getMoodysRating(), "Moody's Rating wasn't saved correctly");
	}

	@Test
	public void testGetUpdateRatingForm() throws Exception {
		var rating = new Rating();
		rating.setMoodysRating("Moody AA");
		rating.setSandpRating("S&P AA");
		rating.setFitchRating("Fitch AA");
		rating.setOrderNumber(20L);
		var savedRating = ratingRepository.save(rating);

		mockMvc.perform(get("/ratings/update/" + savedRating.getId())
						.with(user("user").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(view().name("ratings/update"))
				.andExpect(content().string(containsString("Update Rating")));
	}

	@Test
	public void testUpdateRating() throws Exception {
		var rating = new Rating();
		rating.setMoodysRating("Moody BB");
		rating.setSandpRating("S&P BB");
		rating.setFitchRating("Fitch BB");
		rating.setOrderNumber(30L);
		var savedRating = ratingRepository.save(rating);

		mockMvc.perform(post("/ratings/update/" + savedRating.getId())
						.with(user("user").roles("USER"))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", savedRating.getId().toString())
						.param("moodysRating", "Updated Moody BB")
						.param("sandpRating", "Updated S&P BB")
						.param("fitchRating", "Updated Fitch BB")
						.param("orderNumber", "40"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/ratings/list"));

		var updatedRating = ratingRepository.findById(savedRating.getId()).orElseThrow();
		assertEquals("Updated Moody BB", updatedRating.getMoodysRating(), "Moody's Rating wasn't updated");
		assertEquals("Updated S&P BB", updatedRating.getSandpRating(), "S&P Rating wasn't updated");
		assertEquals("Updated Fitch BB", updatedRating.getFitchRating(), "Fitch Rating wasn't updated");
		assertEquals(40, updatedRating.getOrderNumber(), "Order Number wasn't updated");
	}

	@Test
	public void testDeleteRating() throws Exception {
		var rating = new Rating();
		rating.setMoodysRating("Moody CC");
		rating.setSandpRating("S&P CC");
		rating.setFitchRating("Fitch CC");
		rating.setOrderNumber(50L);
		var savedRating = ratingRepository.save(rating);

		mockMvc.perform(get("/ratings/delete/" + savedRating.getId())
						.with(user("user").roles("USER")))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/ratings/list"));

		assertTrue(ratingRepository.findById(savedRating.getId()).isEmpty(), "Rating wasn't deleted");
	}

}