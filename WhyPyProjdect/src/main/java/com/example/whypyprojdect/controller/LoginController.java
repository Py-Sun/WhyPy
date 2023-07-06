package com.example.whypyprojdect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    //GET
    @GetMapping("/login")
    public String loginPage() {
        return "login-form";
    }

    //POST
    @PostMapping("/login")
    public String loginProcess(
            @RequestParam String id,
            @RequestParam String password,
            Model model ) {
        if (id.equals(password)) {
            model.addAttribute("loginId", id);
        }
        return "login-result";
    }
}
