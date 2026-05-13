package com.project.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.platform.DTO.ChangePasswordDTO;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends IService<User>, UserDetailsService {
    User login(String username, String password);

    boolean registerWithDTO(RegisterRequestDTO registerRequestDTO);

    boolean changePassword(ChangePasswordDTO dto);

    boolean grantAdminRole(Long userId);

    User getUserByName(String username);

    User getUserById(Long id);

    User getUserByEmail(String email);

    User getUserByPhone(String phone);

    boolean banUser(Long userId);

    boolean unbanUser(Long userId);

}
