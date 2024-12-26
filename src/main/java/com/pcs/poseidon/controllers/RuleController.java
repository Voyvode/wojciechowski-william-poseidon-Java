package com.pcs.poseidon.controllers;

import com.pcs.poseidon.domain.Rule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@Slf4j
public class RuleController {
    // TODO: Inject RuleName service

    @RequestMapping("/rules/list")
    public String home(Model model) {
        // TODO: find all RuleName, add to model
        return "rules/list";
    }

    @GetMapping("/rules/add")
    public String addRuleForm(Rule bid) {
        return "rules/add";
    }

    @PostMapping("/rules/validate")
    public String validate(@Valid Rule rule, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        return "rules/add";
    }

    @GetMapping("/rules/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        return "rules/update";
    }

    @PostMapping("/rules/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid Rule rule,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        return "redirect:/rules/list";
    }

    @GetMapping("/rules/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        return "redirect:/rules/list";
    }

}
