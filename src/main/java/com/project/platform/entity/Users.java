package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

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


}
