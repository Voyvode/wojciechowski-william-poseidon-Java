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

import com.pcs.poseidon.domain.CurvePoint;
import com.pcs.poseidon.repositories.CurvePointRepository;

/**
 * The CRUD controller handling HTTP requests for CurvePoint data management.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class CurveController {

    private final CurvePointRepository curvePointRepository;

    @RequestMapping("/curve-points/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointRepository.findAll());
        return "curve-points/list";
    }

    @GetMapping("/curve-points/add")
    public String addForm(CurvePoint curvePoint) {
        return "curve-points/add";
    }

    @PostMapping("/curve-points/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePoint.setCreationDate(LocalDateTime.now());
            curvePointRepository.save(curvePoint);
            model.addAttribute("curvePoints", curvePointRepository.findAll());
            return "redirect:/curve-points/list";
        }
        return "curve-points/add";
    }

    @GetMapping("/curve-points/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        var curvePoint = curvePointRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid curve point id:" + id));
        model.addAttribute("curvePoint", curvePoint);
        return "curve-points/update";
    }

    @PostMapping("/curve-points/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid CurvePoint curvePoint,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curve-points/update";
        }

        curvePointRepository.save(curvePoint);
        model.addAttribute("curve-points", curvePointRepository.findAll());
        return "redirect:/curve-points/list";
    }

    @GetMapping("/curve-points/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        var curvePoint = curvePointRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid curve point id:" + id));
        curvePointRepository.delete(curvePoint);
        model.addAttribute("curvePoints", curvePointRepository.findAll());
        return "redirect:/curve-points/list";
    }

}
