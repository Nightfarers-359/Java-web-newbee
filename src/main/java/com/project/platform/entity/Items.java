package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("Items")
public class Items {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String description;
    private double price;
    private String category;
    @TableField("image_url")
    private String imageUrl;
    @TableField("is_hidden")
    private boolean isHidden;
    @TableField("owner_id")
    private int ownerId;
    @TableField("created_at")
    private java.util.Date createdAt;
    @TableField("updated_at")
    private java.util.Date updatedAt;
}
