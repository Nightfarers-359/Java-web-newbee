package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("Users") // 对应数据库中的Users
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String nickname;

    private String password;

    private String email;

    private String phone;

    private String role;

    @TableField("is_banned")
    private Boolean isBanned;

    @TableField("created_at")
    private Date createdAt;
}