package com.project.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.platform.DTO.ItemData;
import com.project.platform.DTO.ItemInfo;
import com.project.platform.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getItem(@PathVariable Integer id) {
        ItemInfo itemInfo = itemService.getItemById(id);
        return ResponseEntity.ok(itemInfo);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getItemlist() {
        ItemInfo[] itemInfos = itemService.getItemlist();
        return ResponseEntity.ok(itemInfos);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createItem(@RequestBody ItemData itemData) {
        ItemInfo itemInfo = itemService.createItem(itemData);
        return ResponseEntity.ok(itemInfo);
    }
    
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Integer id, @RequestBody ItemData itemData) {
        boolean result = itemService.updateItem(id, itemData);
        if (result) {
            return ResponseEntity.ok("Item updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Item update failed");
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Integer id) {
        boolean result = itemService.deleteItem(id);
        if (result) {
            return ResponseEntity.ok("Item deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Item delete failed");
        }
    }

    @PostMapping("/hide/{id}")
    public ResponseEntity<?> hideItem(@PathVariable Integer id) {
        boolean result = itemService.hideItem(id);
        if (result) {
            return ResponseEntity.ok("Item hidden successfully");
        } else {
            return ResponseEntity.badRequest().body("Item hide failed");
        }
    }
    
    
    
}
