package com.springboot.ecommerce.controller;


import com.springboot.ecommerce.entity.User;
import com.springboot.ecommerce.mapper.UserMapper;
import com.springboot.ecommerce.service.UserService;
import com.springboot.ecommerce.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/register")
    public String register(@RequestBody User user){
        service.register(user);
        return "注册成功";
    }

    @PostMapping("/login")
    public User login(@RequestBody String username, @RequestBody String password){
        return service.login(username,password);
    }
}
