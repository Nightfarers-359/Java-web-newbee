package com.project.platform.service;

import com.project.platform.entity.Users;

public interface UserService {
    //信息查询
    public Users getUserByName(String username);
    public Users getUserById(Long id);
    public Users getUserByEmail(String email);
    public Users getUserByPhone(String phone);
}
