package com.example.SocialAuth.controller;



import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;



@Controller

public class UserController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
        System.out.println(principal);
        if (principal != null) {
            model.addAttribute("name", principal.getAttribute("name"));
        }
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}



