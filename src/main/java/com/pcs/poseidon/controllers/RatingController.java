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
		model.addAttribute("ratings", ratingRepository.findAll());
		return "ratings/list";
	}

	@GetMapping("/ratings/add")
	public String addForm(Rating rating) {
		return "ratings/add";
	}

	@PostMapping("/ratings/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			ratingRepository.save(rating);
			model.addAttribute("ratings", ratingRepository.findAll());
			return "redirect:/ratings/list";
		}
		return "ratings/add";
	}

	@GetMapping("/ratings/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		var rating = ratingRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Invalid rating id:" + id));
		model.addAttribute("rating", rating);
		return "ratings/update";
	}

	@PostMapping("/ratings/update/{id}")
	public String update(@PathVariable("id") Long id, @Valid Rating rating,
						 BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "ratings/update";
		}

		ratingRepository.save(rating);
		model.addAttribute("ratings", ratingRepository.findAll());
		return "redirect:/ratings/list";
	}

	@GetMapping("/ratings/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		var rating = ratingRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Invalid rating id:" + id));
		ratingRepository.delete(rating);
		model.addAttribute("ratings", ratingRepository.findAll());
		return "redirect:/ratings/list";
	}

}
