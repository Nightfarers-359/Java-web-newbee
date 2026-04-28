package com.project.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.platform.entity.Users;
import com.project.platform.service.UserService;

@RestController
@RequestMapping("/user")
public class user {
    @Autowired
    private UserService userService;
    //路由有问题
    @GetMapping("/")
    public Users getUserById(@RequestParam("id") Long id){
        return userService.getUserById(id);
    }
    @GetMapping("/")
    public Users getUserByEmail(@RequestParam("email") String email){
        return userService.getUserByEmail(email);
    }
    @GetMapping
    public Users getUserByPhone(@RequestParam("phone") String phone){
        return userService.getUserByPhone(phone);
    }
    @GetMapping("/")
    public Users getUserByName(@RequestParam("name") String name){
        return userService.getUserByName(name);
    }
}
