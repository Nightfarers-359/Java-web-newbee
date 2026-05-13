package com.project.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.platform.DTO.LoginRequestDTO;
import com.project.platform.DTO.LoginResponseDTO;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.DTO.UpdateUserDTO;
import com.project.platform.DTO.UserResponseDTO;
import com.project.platform.common.Result;
import com.project.platform.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * 接口地址：POST /api/users/register
     */
    @PostMapping("/register")
    public Result<UserResponseDTO> register(@RequestBody RegisterRequestDTO registerRequest) {
        // 调用 Service 层处理注册逻辑
        UserResponseDTO user = userService.register(registerRequest);
        return Result.success("注册成功", user);
    }

    /**
     * 用户登录
     * 接口地址：POST /api/users/login
     */
    @PostMapping("/login")
    public Result<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        // 调用 Service 层处理登录逻辑，返回 Token 和用户信息
        LoginResponseDTO loginResponse = userService.login(loginRequest);
        return Result.success("登录成功", loginResponse);
    }

    /**
     * 获取用户详情
     * 接口地址：GET /api/users/{id}
     * @PathVariable 用于接收 URL 路径中的参数（例如 /api/users/1001）
     */
    @GetMapping("/{id}")
    public Result<UserResponseDTO> getUserInfo(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserInfoById(id);
        return Result.success("获取成功", user);
    }

    /**
     * 4. 更新用户信息
     * 接口地址：PUT /api/users/{id}
     * 同时使用了 @PathVariable 接收 ID，@RequestBody 接收前端传来的更新字段
     */
    @PutMapping("/{id}")
    public Result<UserResponseDTO> updateUserInfo(
            @PathVariable Long id, 
            @RequestBody UpdateUserDTO updateRequest) {
        
        UserResponseDTO updatedUser = userService.updateUserInfo(id, updateRequest);
        return Result.success("更新成功", updatedUser);
    }

    /**
     * 5. 用户登出
     * 接口地址：POST /api/users/logout
     * 登出通常需要将 Token 放入请求头（Header）中传给后端
     */
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        // 如果前端传的是 "Bearer xxxxx"，通常需要截取一下，这里假设直接传 token 或已处理
        userService.logout(token);
        return Result.success("登出成功", null);
    }
}