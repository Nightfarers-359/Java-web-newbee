package com.project.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.platform.entity.User;
import com.project.platform.service.UserService;

@RestController
@RequestMapping("/user")
public class user {
    @Autowired
    private UserService userService;

    // 路由有问题
    @GetMapping("/")
    public User getUserById(@RequestParam("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/")
    public User getUserByEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping
    public User getUserByPhone(@RequestParam("phone") String phone) {
        return userService.getUserByPhone(phone);
    }

    @GetMapping("/")
    public User getUserByName(@RequestParam("name") String name) {
        return userService.getUserByName(name);
    }
}
