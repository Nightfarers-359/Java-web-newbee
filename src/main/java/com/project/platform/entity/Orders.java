package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("Orders")
public class Orders {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("user_id")
    private int userId;
    @TableField("item_id")
    private int itemId;
    private int quantity;
    @TableField("total_price")
    private double totalPrice;
    @TableField("is_paid")
    private boolean isPaid;
    @TableField("created_at")
    private java.util.Date createdAt;
    @TableField("updated_at")
    private java.util.Date updatedAt;
}
