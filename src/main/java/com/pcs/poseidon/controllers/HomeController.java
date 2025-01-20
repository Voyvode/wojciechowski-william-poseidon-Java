package com.pcs.poseidon.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

	@RequestMapping("/")
	public String home(Authentication authentication) {
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

		if (isAdmin) {
			return "redirect:/admin/users/list";
		} else {
			return "redirect:/bids/list";
		}
	}

}
