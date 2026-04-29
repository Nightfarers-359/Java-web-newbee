package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("Orders")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_buyer_id")
    private Integer buyerId;
    @TableField("user_seller_id")
    private Integer sellerId;
    @TableField("item_id")
    private Integer itemId;
    private Integer quantity;
    @TableField("total_price")
    private double totalPrice;
    @TableField("is_paid")
    private boolean isPaid;
    @TableField("created_at")
    private java.util.Date createdAt;
    @TableField("updated_at")
    private java.util.Date updatedAt;
    @TableField("deleted_at")
    private java.util.Date deletedAt;
}
