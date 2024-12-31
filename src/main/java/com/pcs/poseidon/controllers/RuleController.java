package com.pcs.poseidon.controllers;

import com.pcs.poseidon.domain.Rule;
import com.pcs.poseidon.repositories.RuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

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
        model.addAttribute("rules", ruleRepository.findAll());
        return "rules/list";
    }

    @GetMapping("/rules/add")
    public String addRuleForm(Rule rule) {
        return "rules/add";
    }

    @PostMapping("/rules/validate")
    public String validate(@Valid Rule rule, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleRepository.save(rule);
            model.addAttribute("rules", ruleRepository.findAll());
            return "redirect:/rules/list";
        }
        return "rules/add";
    }

    @GetMapping("/rules/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        var rule = ruleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid rule id:" + id));
        model.addAttribute("rule", rule);
        return "rules/update";
    }

    @PostMapping("/rules/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid Rule rule,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rules/update";
        }
        ruleRepository.save(rule);
        model.addAttribute("rules", ruleRepository.findAll());
        return "redirect:/rules/list";
    }

    @GetMapping("/rules/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        var rule = ruleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid rule id:" + id));
        ruleRepository.delete(rule);
        model.addAttribute("rules", ruleRepository.findAll());
        return "redirect:/rules/list";
    }

}
