package com.project.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.platform.entity.*;
import com.project.platform.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user != null) {
            return "Login succeed, user id: " + user.getId() + ".";
        }
        return "User name or password error.";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        boolean isSuccess = userService.register(user);
        return isSuccess ? "Register succeed." : "User already existed.";

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
