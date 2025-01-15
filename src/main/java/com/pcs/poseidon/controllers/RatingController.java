package com.pcs.poseidon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.pcs.poseidon.domain.Rating;
import com.pcs.poseidon.repositories.RatingRepository;

/**
 * The CRUD controller handling HTTP requests for Rating data management.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class RatingController {

	private final RatingRepository ratingRepository;

	@RequestMapping("/ratings/list")
	public String home(Model model) {
		log.info("Fetching list of all ratings");
		model.addAttribute("ratings", ratingRepository.findAll());
		return "ratings/list";
	}

	@GetMapping("/ratings/add")
	public String addForm() {
		log.info("Displaying rating add form");
		return "ratings/add";
	}

	@PostMapping("/ratings/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			log.info("Saving a new rating: {}", rating);
			ratingRepository.save(rating);
			log.info("Rating saved successfully");
			model.addAttribute("ratings", ratingRepository.findAll());
			return "redirect:/ratings/list";
		}
		log.warn("Validation failed for rating: {}", result.getFieldErrors());
		return "ratings/add";
	}

	@GetMapping("/ratings/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		log.info("Fetching rating for update, ID: {}", id);
		var rating = ratingRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Invalid rating id: " + id));
		model.addAttribute("rating", rating);
		log.info("Displaying update form for rating, ID: {}", id);
		return "ratings/update";
	}

	@PostMapping("/ratings/update/{id}")
	public String update(@PathVariable("id") Long id, @Valid Rating rating,
						 BindingResult result, Model model) {
		if (result.hasErrors()) {
			log.warn("Validation errors occurred while updating rating, ID: {}: {}", id, result.getFieldErrors());
			return "ratings/update";
		}
		log.info("Updating rating, ID: {}", id);
		ratingRepository.save(rating);
		log.info("Rating updated successfully, ID: {}", id);
		model.addAttribute("ratings", ratingRepository.findAll());
		return "redirect:/ratings/list";
	}

	@GetMapping("/ratings/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		log.info("Deleting rating, ID: {}", id);
		var rating = ratingRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Invalid rating id: " + id));
		ratingRepository.delete(rating);
		log.info("Rating deleted successfully, ID: {}", id);
		model.addAttribute("ratings", ratingRepository.findAll());
		return "redirect:/ratings/list";
	}

}
