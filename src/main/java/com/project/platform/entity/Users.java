package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("Users")
public class Users {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String password;
    private String email;
    private String phone;
    @TableField("is_admin")
    private boolean isAdmin;
    @TableField("is_banned")
    private boolean isBanned;
    @TableField("is_seller")
    private boolean isSeller;
    @TableField("created_at")
    private java.util.Date createdAt;

}
