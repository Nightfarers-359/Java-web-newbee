package com.project.platform.service;

import com.project.platform.DTO.LoginRequestDTO;
import com.project.platform.DTO.LoginResponseDTO;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.DTO.UpdateUserDTO;
import com.project.platform.DTO.UserResponseDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.platform.DTO.ChangePasswordDTO;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
/**
 * 用户服务接口
 * 定义了用户注册、登录、信息获取及登出的核心业务逻辑
 */



public interface UserService extends IService<User>, UserDetailsService {
    User login(String username, String password);


    /**
     * 根据用户ID获取用户详情
     * 
     * @param id 用户唯一标识
     * @return UserResponseDTO 用户详细信息
     */
    UserResponseDTO getUserInfoById(Long id);


 /**
 * 更新用户信息（部分更新）
 * 
 * @param id 需要更新的用户ID
 * @param updateRequest 包含需要修改字段的DTO
 * @return 更新后的用户信息
 */
UserResponseDTO updateUserInfo(Long id, UpdateUserDTO updateRequest);
}

    boolean changePassword(ChangePasswordDTO dto);

    boolean grantAdminRole(Long userId);

    boolean banUser(Long userId);

    boolean unbanUser(Long userId);

}

