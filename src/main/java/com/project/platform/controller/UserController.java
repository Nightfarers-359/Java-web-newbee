package com.project.platform.controller;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.fs.LocatedFileStatus;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.platform.DTO.ChangePasswordDTO;
import com.project.platform.entity.User;
import com.project.platform.service.UserService;
import com.project.platform.util.HdfsUtil;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private HdfsUtil hdfsUtil;
    /**
     * 获取用户详情
     * 接口地址：GET /user/{id}
     * @PathVariable 用于接收 URL 路径中的参数（users/id）
     */
    @GetMapping("/{id}")
    public Result<UserResponseDTO> getUserInfo(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserInfoById(id);
        return Result.success("获取成功", user);
    }
  
     /**
     * 更新用户信息
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
  
  

    @PostMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto) {
        // 此处可添加 JSR-303 参数校验，例如 @Valid
        userService.changePassword(dto);
        return ResponseEntity.ok("密码修改成功");
    }

    // 请求示例: GET /user?email=iam@yourdad.com
    // 请求示例: GET /user?phone=13866668888
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // 确保仅管理员可以调用
    public User getUser(@RequestParam(required = false) Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone) {
        if (id != null)
            return userService.getUserById(id);
        if (email != null)
            return userService.getUserByEmail(email);
        if (phone != null)
            return userService.getUserByPhone(phone);
        throw new IllegalArgumentException("必须提供查询参数");
    }

    @GetMapping("/id/{username}")
    public ResponseEntity<Long> getUserIdByUsername(@PathVariable String username) {
        User user = userService.getUserByName(username);
        if (user != null) {
            return ResponseEntity.ok(user.getId());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
