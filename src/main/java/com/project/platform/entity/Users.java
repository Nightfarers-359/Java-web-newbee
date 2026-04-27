package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import lombok.Data;

@Data
@TableName("Users")
public class Users {
    @Setter
    @Getter
    @TableId(type = IdType.AUTO)
    private Long id;
    @Setter
    @Getter
    private String name;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String phone;
    private boolean is_admin;
    private boolean is_banned;
    private boolean is_seller;
    @Getter
    @Setter
    private java.util.Date created_at;
    @TableField("is_admin")
    private boolean isAdmin;
    @TableField("is_banned")
    private boolean isBanned;
    @TableField("is_seller")
    private boolean isSeller;
    @TableField("created_at")
    private java.util.Date createdAt;

}

