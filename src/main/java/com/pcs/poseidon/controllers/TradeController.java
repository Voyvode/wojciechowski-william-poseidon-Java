package com.pcs.poseidon.controllers;

import com.pcs.poseidon.domain.Trade;
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
public class TradeController {
    // TODO: Inject Trade service

    @RequestMapping("/trades/list")
    public String home(Model model) {
        // TODO: find all Trade, add to model
        return "trades/list";
    }

    @GetMapping("/trades/add")
    public String addUser(Trade bid) {
        return "trades/add";
    }

    @PostMapping("/trades/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list
        return "trades/add";
    }

    @GetMapping("/trades/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        return "trades/update";
    }

    @PostMapping("/trades/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list
        return "redirect:/trades/list";
    }

    @GetMapping("/trades/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        return "redirect:/trades/list";
    }

}
