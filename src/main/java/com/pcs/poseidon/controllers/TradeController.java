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

import com.pcs.poseidon.domain.Trade;
import com.pcs.poseidon.repositories.TradeRepository;

/**
 * The CRUD controller handling HTTP requests for Trade data management.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class TradeController {

    private final TradeRepository tradeRepository;

    @RequestMapping("/trades/list")
    public String home(Model model) {
        log.info("Fetching list of all trades");
        model.addAttribute("trades", tradeRepository.findAll());
        return "trades/list";
    }

    @GetMapping("/trades/add")
    public String add(Model model) {
        log.info("Displaying trade add form");
        model.addAttribute("trade", new Trade());
        return "trades/add";
    }

    @PostMapping("/trades/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            log.info("Saving a new trade: {}", trade);
            trade.setCreationDate(LocalDateTime.now());
            tradeRepository.save(trade);
            log.info("Trade saved successfully");
            model.addAttribute("trades", tradeRepository.findAll());
            return "redirect:/trades/list";
        }
        log.warn("Validation failed for trade: {}", result.getFieldErrors());
        return "trades/add";
    }

    @GetMapping("/trades/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        log.info("Fetching trade for update, ID: {}", id);
        var trade = tradeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid trade id: " + id));
        model.addAttribute("trade", trade);
        log.info("Displaying update form for trade, ID: {}", id);
        return "trades/update";
    }

    @PostMapping("/trades/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid Trade trade,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("Validation errors occurred while updating trade, ID: {}: {}", id, result.getFieldErrors());
            return "trades/update";
        }
        log.info("Updating trade, ID: {}", id);
        trade.setRevisionDate(LocalDateTime.now());
        tradeRepository.save(trade);
        log.info("Trade updated successfully, ID: {}", id);
        model.addAttribute("trades", tradeRepository.findAll());
        return "redirect:/trades/list";
    }

    @GetMapping("/trades/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        log.info("Deleting trade, ID: {}", id);
        var trade = tradeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid trade id: " + id));
        tradeRepository.delete(trade);
        log.info("Trade deleted successfully, ID: {}", id);
        model.addAttribute("trades", tradeRepository.findAll());
        return "redirect:/trades/list";
    }

}