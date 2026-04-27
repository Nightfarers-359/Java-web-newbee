package com.project.platform.service;

import com.project.platform.entity.BaseClass.LoginData;
import com.baomidou.mybatisplus.extension.service.IService;

import com.project.platform.entity.User;

public interface UserService extends IService<User> {
    User login(String username, String password);

    boolean register(User user);
    // TO DO
}
