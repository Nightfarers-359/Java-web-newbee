package com.project.platform.controller;

import com.project.platform.entity.Users;
import com.project.platform.service.UserService;
import com.project.platform.service.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class user {
    @Autowired
    private UserServiceImp userService;

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
