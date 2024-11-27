package com.example.lab5UserTask.controller;

import com.example.lab5UserTask.model.User;
import com.example.lab5UserTask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password. Please try again.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model,
                                       @RequestParam(value = "error", required = false) String error,
                                       @RequestParam(value = "success", required = false) String success) {
        model.addAttribute("user", new User());
        if (error != null) {
            model.addAttribute("error", "Username already exists. Please choose a different one.");
        }
        if (success != null) {
            model.addAttribute("message", "Registration successful! Please log in.");
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        if (userService.usernameExists(user.getUsername())) {
            return "redirect:/register?error";
        }
        userService.registerUser(user);
        return "redirect:/login?success";
    }
}
