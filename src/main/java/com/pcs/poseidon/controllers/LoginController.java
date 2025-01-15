package com.pcs.poseidon.controllers;

import com.pcs.poseidon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("app")
@Slf4j
public class LoginController {

    private final UserRepository userRepository;

    @GetMapping("login")
    public ModelAndView login() {
        log.info("Displaying login page");
        var mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        var mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("users/list");
        return mav;
    }

    @GetMapping("error")
    public ModelAndView error() {
        var mav = new ModelAndView();
        var errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

}
