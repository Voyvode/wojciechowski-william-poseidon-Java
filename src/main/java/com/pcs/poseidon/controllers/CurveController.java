package com.pcs.poseidon.controllers;

import com.pcs.poseidon.domain.CurvePoint;
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
public class CurveController {
    // TODO: Inject Curve Point service

    @RequestMapping("/curve-points/list")
    public String home(Model model) {
        // TODO: find all Curve Point, add to model
        return "curve-points/list";
    }

    @GetMapping("/curve-points/add")
    public String addBidForm(CurvePoint bid) {
        return "curve-points/add";
    }

    @PostMapping("/curve-points/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        return "curve-points/add";
    }

    @GetMapping("/curve-points/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        return "curve-points/update";
    }

    @PostMapping("/curve-points/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        return "redirect:/curve-points/list";
    }

    @GetMapping("/curve-points/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        return "redirect:/curve-points/list";
    }

}
