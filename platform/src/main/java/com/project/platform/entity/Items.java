package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("Items")
public class Items {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String description;
    private double price;
    private String category;
    private String image_url;
    private boolean is_hidden;
    private int owner_id;
    private java.util.Date created_at;
    private java.util.Date updated_at;
}
