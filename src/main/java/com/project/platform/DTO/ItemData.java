package com.project.platform.DTO;

import lombok.Data;

@Data
public class ItemData {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private String image;
    private Integer ownerId;
}
