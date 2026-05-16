package com.project.platform.DTO;

import lombok.Data;

@Data
public class ItemInfo {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private String imageUrl;
    
    public ItemInfo(Integer id, String name, String description, Double price, Integer stock, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.imageUrl = imageUrl;
    }
}
