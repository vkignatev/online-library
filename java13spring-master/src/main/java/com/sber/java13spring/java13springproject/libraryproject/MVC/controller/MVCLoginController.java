package com.sber.java13spring.java13springproject.libraryproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Hidden
@RequestMapping("/login")
public class MVCLoginController {
    
    @GetMapping("")
    public String login() {
        return "login";
    }
    
}
