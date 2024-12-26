package com.pcs.poseidon.controllers;

import com.pcs.poseidon.domain.Bid;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class BidController {
	// TODO: Inject Bid service

	@RequestMapping("/bids/list")
	public String home(Model model) {
		// TODO: call service find all bids to show to the view
		return "bids/list";
	}

	@GetMapping("/bids/add")
	public String addBidForm(Bid bid) {
		return "bids/add";
	}

	@PostMapping("/bids/validate")
	public String validate(@Valid Bid bid, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return bid list
		return "bids/add";
	}

	@GetMapping("/bids/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get Bid by Id and to model then show to the form
		return "bids/update";
	}

	@PostMapping("/bids/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid, BindingResult result, Model model) {
		// TODO: check required fields, if valid call service to update Bid and return list Bid
		return "redirect:/bids/list";
	}

	@GetMapping("/bids/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Bid by Id and delete the bid, return to Bid list
		return "redirect:/bids/list";
	}

}
