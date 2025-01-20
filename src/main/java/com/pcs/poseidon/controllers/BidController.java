package com.pcs.poseidon.controllers;

import java.time.LocalDateTime;

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

import com.pcs.poseidon.domain.Bid;
import com.pcs.poseidon.repositories.BidRepository;

/**
 * The CRUD controller handling HTTP requests for Bid data management.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class BidController {

	private final BidRepository bidRepository;

	@RequestMapping("/bids/list")
	public String home(Model model) {
		log.info("Fetching list of all bids");
		model.addAttribute("bids", bidRepository.findAll());
		return "bids/list";
	}

	@GetMapping("/bids/add")
	public String addForm(Model model) {
		log.info("Displaying bid add form");
		model.addAttribute("bid", new Bid());
		return "bids/add";
	}

	@PostMapping("/bids/validate")
	public String validate(@Valid Bid bid, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			log.info("Saving a new bid: {}", bid);
			bid.setCreationDate(LocalDateTime.now());
			bidRepository.save(bid);
			log.info("Bid saved successfully");
			model.addAttribute("bids", bidRepository.findAll());
			return "redirect:/bids/list";
		}
		log.warn("Validation errors occurred: {}", result.getFieldErrors());
		return "bids/add";
	}

	@GetMapping("/bids/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		log.info("Fetching bid for update, ID: {}", id);
		var bid = bidRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Invalid bid id: " + id));
		model.addAttribute("bid", bid);
		log.info("Displaying update form for bid, ID: {}", id);
		return "bids/update";
	}

	@PostMapping("/bids/update/{id}")
	public String update(@PathVariable("id") Integer id, @Valid Bid bid,
						 BindingResult result, Model model) {
		if (result.hasErrors()) {
			log.warn("Validation errors occurred while updating bid, ID: {}: {}", id, result.getFieldErrors());
			return "bids/update";
		}
		log.info("Updating bid, ID: {}", id);
		bid.setRevisionDate(LocalDateTime.now());
		bidRepository.save(bid);
		log.info("Bid updated successfully, ID: {}", id);
		model.addAttribute("bids", bidRepository.findAll());
		return "redirect:/bids/list";
	}

	@GetMapping("/bids/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		log.info("Deleting bid, ID: {}", id);
		var bid = bidRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Invalid bid id: " + id));
		bidRepository.delete(bid);
		log.info("Bid deleted successfully, ID: {}", id);
		model.addAttribute("bids", bidRepository.findAll());
		return "redirect:/bids/list";
	}

}