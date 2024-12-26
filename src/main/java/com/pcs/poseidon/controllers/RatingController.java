package com.pcs.poseidon.controllers;

import com.pcs.poseidon.domain.Rating;
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
public class RatingController {
    // TODO: Inject Rating service

    @RequestMapping("/ratings/list")
    public String home(Model model) {
        // TODO: find all Rating, add to model
        return "ratings/list";
    }

    @GetMapping("/ratings/add")
    public String addRatingForm(Rating rating) {
        return "ratings/add";
    }

    @PostMapping("/ratings/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        return "ratings/add";
    }

    @GetMapping("/ratings/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        return "ratings/update";
    }

    @PostMapping("/ratings/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        return "redirect:/ratings/list";
    }

    @GetMapping("/ratings/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        return "redirect:/ratings/list";
    }

}
