package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO) // 指定主键且为自增
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String phone;
    private String role;
    private boolean is_banned;
    private java.util.Date created_at;

}
