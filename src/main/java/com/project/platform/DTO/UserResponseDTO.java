package com.project.platform.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.platform.entity.User;

import lombok.Data;

// 用于返回给前端的用户信息，不包含密码
@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String role;
    private Boolean isBanned;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.isBanned = user.getIsBanned();
        this.createdAt = user.getCreatedAt();
    }

}