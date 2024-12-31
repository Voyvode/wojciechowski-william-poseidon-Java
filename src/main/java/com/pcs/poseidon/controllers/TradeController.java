package com.pcs.poseidon.controllers;

import com.pcs.poseidon.domain.Trade;
import com.pcs.poseidon.repositories.TradeRepository;
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

import java.time.LocalDateTime;

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
        model.addAttribute("trades", tradeRepository.findAll());
        return "trades/list";
    }

    @GetMapping("/trades/add")
    public String add(Trade trade) {
        return "trades/add";
    }

    @PostMapping("/trades/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            trade.setCreationDate(LocalDateTime.now());
            tradeRepository.save(trade);
            model.addAttribute("trades", tradeRepository.findAll());
            return "redirect:/trades/list";
        }
        return "trades/add";
    }

    @GetMapping("/trades/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        var trade = tradeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid trade id:" + id));
        model.addAttribute("trade", trade);
        return "trades/update";
    }

    @PostMapping("/trades/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid Trade trade,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rules/update";
        }
        trade.setRevisionDate(LocalDateTime.now());
        tradeRepository.save(trade);
        model.addAttribute("trades", tradeRepository.findAll());
        return "redirect:/trades/list";
    }

    @GetMapping("/trades/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        var trade = tradeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid trade id:" + id));
        tradeRepository.delete(trade);
        model.addAttribute("trades", tradeRepository.findAll());
        return "redirect:/trades/list";
    }

}
