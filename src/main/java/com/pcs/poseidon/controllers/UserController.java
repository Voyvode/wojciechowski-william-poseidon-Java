package com.pcs.poseidon.controllers;

import com.pcs.poseidon.domain.User;
import com.pcs.poseidon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

/**
 * The CRUD controller handling HTTP requests for managing users.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    @RequestMapping("/admin/users/list")
    public String home(Model model) {
        log.info("Fetching list of all users");
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/admin/users/add")
    public String addUser() {
        log.info("Displaying user add form");
        return "users/add";
    }

    @PostMapping("/admin/users/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            log.info("Saving a new user: {}", user.getUsername());
            var encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            log.info("User saved successfully: {}", user.getUsername());
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/admin/users/list";
        }
        log.warn("Validation failed for user: {}", result.getFieldErrors());
        return "users/add";
    }

    @GetMapping("/admin/users/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        log.info("Fetching user for update, ID: {}", id);
        var user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid user ID: " + id));
        user.setPassword(""); // Clear the password for the update form
        model.addAttribute("user", user);
        log.info("Displaying update form for user, ID: {}", id);
        return "users/update";
    }

    @PostMapping("/admin/users/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("Validation errors occurred while updating user, ID: {}: {}", id, result.getFieldErrors());
            return "users/update";
        }
        log.info("Updating user, ID: {}", id);
        var encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        log.info("User updated successfully, ID: {}", id);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/admin/users/list";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        log.info("Deleting user, ID: {}", id);
        var user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid user ID: " + id));
        userRepository.delete(user);
        log.info("User deleted successfully, ID: {}", id);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/admin/users/list";
    }

}