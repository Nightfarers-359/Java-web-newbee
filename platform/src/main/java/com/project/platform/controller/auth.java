package com.project.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class auth {
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/ban")
    public String ban() {
        return "ban";
    }

    @GetMapping("/refresh")
    public String refresh() {
        return "refresh";
    }

    @GetMapping("/changePassword")
    public String changePassword() {
        return "changePassword";
    }
}
