package com.project.platform.service;

import com.project.platform.DTO.LoginRequestDTO;
import com.project.platform.DTO.LoginResponseDTO;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.DTO.UpdateUserDTO;
import com.project.platform.DTO.UserResponseDTO;

/**
 * 用户服务接口
 * 定义了用户注册、登录、信息获取及登出的核心业务逻辑
 */
public interface UserService {

    /**
     * 用户注册
     * 
     * @param registerRequest 注册请求数据 (包含用户名, 密码, 邮箱等)
     * @return UserResponseDTO 注册成功后的用户信息 (不包含密码)
     */
    UserResponseDTO register(RegisterRequestDTO registerRequest);

    /**
     * 用户登录
     * 
     * @param loginRequest 登录请求数据 (包含账号/邮箱, 密码)
     * @return LoginResponseDTO 包含 JWT Token 和 用户基础信息
     */
    LoginResponseDTO login(LoginRequestDTO loginRequest);

    /**
     * 根据用户ID获取用户详情
     * 
     * @param id 用户唯一标识
     * @return UserResponseDTO 用户详细信息
     */
    UserResponseDTO getUserInfoById(Long id);

    /**
     * 用户登出
     * 将 Token 加入黑名单或执行相关清理逻辑
     * 
     * @param token 当前用户的 JWT Token
     */
    void logout(String token);

    /**
 * 更新用户信息（部分更新）
 * 
 * @param id 需要更新的用户ID
 * @param updateRequest 包含需要修改字段的DTO
 * @return 更新后的用户信息
 */
UserResponseDTO updateUserInfo(Long id, UpdateUserDTO updateRequest);
}