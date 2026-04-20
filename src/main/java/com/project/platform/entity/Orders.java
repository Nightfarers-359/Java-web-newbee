package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("Orders")
public class Orders {
    @TableId(type = IdType.AUTO)
    private int id;
    private int user_id;
    private int item_id;
    private int quantity;
    private double total_price;
    private boolean is_paid;
    private java.util.Date created_at;
    private java.util.Date updated_at;
}
