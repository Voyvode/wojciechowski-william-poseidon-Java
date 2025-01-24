package com.pcs.poseidon.controllers;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcs.poseidon.domain.Rule;
import com.pcs.poseidon.repositories.RuleRepository;

/**
 * The CRUD controller handling HTTP requests for Rule data management.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class RuleController {

	private final RuleRepository ruleRepository;

	@RequestMapping("/rules/list")
	public String home(Model model) {
		log.info("Fetching list of all rules");
		model.addAttribute("rules", ruleRepository.findAll());
		return "rules/list";
	}

	@GetMapping("/rules/add")
	public String addRuleForm(Model model) {
		log.info("Displaying rule add form");
		model.addAttribute("rule", new Rule());
		return "rules/add";
	}

	@PostMapping("/rules/validate")
	public String validate(@Valid Rule rule, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			log.info("Saving a new rule: {}", rule);
			ruleRepository.save(rule);
			log.info("Rule saved successfully");
			model.addAttribute("rules", ruleRepository.findAll());
			return "redirect:/rules/list";
		}
		log.warn("Validation failed for rule: {}", result.getFieldErrors());
		return "rules/add";
	}

	@GetMapping("/rules/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		log.info("Fetching rule for update, ID: {}", id);
		var rule = ruleRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Invalid rule id: " + id));
		model.addAttribute("rule", rule);
		log.info("Displaying update form for rule, ID: {}", id);
		return "rules/update";
	}

	@PostMapping("/rules/update/{id}")
	public String update(@PathVariable("id") Long id, @Valid Rule rule,
						 BindingResult result, Model model) {
		if (result.hasErrors()) {
			log.warn("Validation errors occurred while updating rule, ID: {}: {}", id, result.getFieldErrors());
			return "rules/update";
		}
		log.info("Updating rule, ID: {}", id);
		ruleRepository.save(rule);
		log.info("Rule updated successfully, ID: {}", id);
		model.addAttribute("rules", ruleRepository.findAll());
		return "redirect:/rules/list";
	}

	@GetMapping("/rules/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		log.info("Deleting rule, ID: {}", id);
		var rule = ruleRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Invalid rule id: " + id));
		ruleRepository.delete(rule);
		log.info("Rule deleted successfully, ID: {}", id);
		model.addAttribute("rules", ruleRepository.findAll());
		return "redirect:/rules/list";
	}

}