package com.project.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.entity.User;

public interface UserService extends IService<User> {
    User login(String username, String password);

    boolean registerWithDTO(RegisterRequestDTO registerRequestDTO);

    public User getUserByName(String username);

    public User getUserById(Long id);

    // 注：IService已经内置了一个getById(Serializeable id)方法，这里为了统一性先保留
    public User getUserByEmail(String email);

    public User getUserByPhone(String phone);
    // TO DO
}
