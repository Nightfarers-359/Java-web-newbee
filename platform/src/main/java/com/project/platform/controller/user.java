package com.project.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class user {
    @GetMapping("/{id}/info")
    public String info(@PathVariable String id) {
        return "info " + id;
    }

    @GetMapping("/{id}/shoppingList")
    public String shoppingList(@PathVariable String id) {
        return "shoppingList " + id;
    }

    
}
