package com.security.sga.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.security.sga.Authentication.Dto.LoginRequest;

@Controller
@RequestMapping("/")
public class AppController {

    @GetMapping("/home")
    public String home() {
        return "index";
    }
 
    @GetMapping("/about")
    public String about() {
        return "about";
    }
 
    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }
 
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @GetMapping("/login")
    public String login(Model model) {
        LoginRequest loginRequest = new LoginRequest();
        model.addAttribute("loginRequest", loginRequest);
        return "login";
    }
}