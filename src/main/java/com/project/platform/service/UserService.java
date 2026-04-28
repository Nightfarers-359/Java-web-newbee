package com.project.platform.service;

import com.project.platform.entity.BaseClass.LoginData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.platform.entity.User;

public interface UserService extends IService<User> {
    User login(String username, String password);

    boolean register(User user);

    public User getUserByName(String username);

    public User getUserById(Long id);

    // 注：IService已经内置了一个getById(Serializeable id)方法，这里为了统一性先保留
    public User getUserByEmail(String email);

    public User getUserByPhone(String phone);
    // TO DO
}
