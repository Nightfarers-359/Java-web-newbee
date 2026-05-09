package com.project.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.platform.entity.User;
import com.project.platform.DTO.RegisterRequestDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends IService<User>, UserDetailsService {
    User login(String username, String password);

    boolean registerWithDTO(RegisterRequestDTO registerRequestDTO);

    User getUserByName(String username);

    User getUserById(Long id);

    User getUserByEmail(String email);

    User getUserByPhone(String phone);
}

