package com.project.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.platform.entity.User;
import com.project.platform.service.UserService;

@RestController
@RequestMapping("/user")
public class user {
    @Autowired
    private UserService userService;

    // 路由有问题
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/{phone}")
    public User getUserByPhone(@PathVariable("phone") String phone) {
        return userService.getUserByPhone(phone);
    }

    @GetMapping("/{name}")
    public User getUserByName(@PathVariable("name") String name) {
        return userService.getUserByName(name);
    }
}
