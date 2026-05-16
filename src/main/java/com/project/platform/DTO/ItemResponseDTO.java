package com.project.platform.DTO;

import com.project.platform.entity.Item;
import lombok.Data;
import java.util.Date;

@Data
public class ItemResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
    private Boolean isHidden;
    private Integer ownerId;
    private Date createdAt;
    private Date updatedAt;

    public ItemResponseDTO(Item item) {
        this.id=item.getId();
        this.name=item.getName();
        this.description=item.getDescription();
        this.price=item.getPrice();
        this.category=item.getCategory();
        this.imageUrl=item.getImageUrl();
        this.isHidden=item.isHidden();
        this.ownerId=item.getOwnerId();
        this.createdAt=item.getCreatedAt();
        this.updatedAt=item.getUpdatedAt();
    }
}