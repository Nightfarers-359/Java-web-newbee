package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("Users")
public class Users {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private boolean is_admin;
    private boolean is_banned;
    private boolean is_seller;
    private java.util.Date created_at;

}
