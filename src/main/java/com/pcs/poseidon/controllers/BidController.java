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
		model.addAttribute("bids", bidRepository.findAll());
		return "bids/list";
	}

	@GetMapping("/bids/add")
	public String addForm(Bid bid) {
		return "bids/add";
	}

	@PostMapping("/bids/validate")
	public String validate(@Valid Bid bid, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			bid.setCreationDate(LocalDateTime.now ());
			bidRepository.save(bid);
			model.addAttribute("bids", bidRepository.findAll());
			return "redirect:/bids/list";
		}
		return "bids/add";
	}

	@GetMapping("/bids/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		var bid = bidRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Invalid bid id:" + id));
		model.addAttribute("bid", bid);
		return "bids/update";
	}

	@PostMapping("/bids/update/{id}")
	public String update(@PathVariable("id") Integer id, @Valid Bid bid,
						 BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "bids/update";
		}
		bid.setRevisionDate(LocalDateTime.now());
		bidRepository.save(bid);
		model.addAttribute("bids", bidRepository.findAll());
		return "redirect:/bids/list";
	}

	@GetMapping("/bids/delete/{id}")
	public String delete(@PathVariable("id") Integer id, Model model) {
		var bid = bidRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Invalid bid id:" + id));
		bidRepository.delete(bid);
		model.addAttribute("bids", bidRepository.findAll());
		return "redirect:/bids/list";
	}

}
