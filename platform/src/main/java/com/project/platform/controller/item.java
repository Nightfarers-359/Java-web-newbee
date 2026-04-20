package com.project.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class item {
    @GetMapping("/{category}/list")
    public String list(@PathVariable String category) {
        return "list " + category;
    }
    
    @GetMapping("/{itemId}/info")
    public String info(@PathVariable String itemId) {
        return "info " + itemId;
    }
    
}
