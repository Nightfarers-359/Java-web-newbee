package com.project.platform.controller;

import com.project.platform.entity.Users;
import com.project.platform.service.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.platform.DTO.JWTpayload;
import com.project.platform.DTO.UserUpdateDTO;
import com.project.platform.entity.User;
import com.project.platform.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/user")
public class user {
    @Autowired
    private UserService userService;

    /**
     * 用户查看自己
     */
    //使用ResponseEntity返回状态码和请求体
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        JWTpayload payload = getCurrentPayload(request);
        if (payload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未认证");
        }
        User user = userService.getById(payload.getId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
        }
        // 移除密码
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    /**
     * 根据 id 查询用户（管理员可查任意用户，普通用户只能查自己）
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, HttpServletRequest request) {
        JWTpayload payload = getCurrentPayload(request);
        if (payload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未认证");
        }

        // 权限校验
        boolean isAdmin = payload.isAdmin();
        Long currentUserId = payload.getId();
        if (!isAdmin && !currentUserId.equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("无权查看其他用户信息");
        }

        User user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
        }
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    /**
     * 更新当前用户信息
     */
    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUser(@Valid @RequestBody UserUpdateDTO updateDTO,
                                               HttpServletRequest request) {
        JWTpayload payload = getCurrentPayload(request);
        if (payload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未认证");
        }
        User user = userService.getById(payload.getId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
        }
        // 更新允许修改的字段
        if (updateDTO.getNickname() != null) {
            user.setNickname(updateDTO.getNickname());
        }
        if (updateDTO.getPhone() != null) {
            user.setPhone(updateDTO.getPhone());
        }
        boolean updated = userService.updateById(user);
        if (updated) {
            User updatedUser = userService.getById(payload.getId());
            updatedUser.setPassword(null);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败");
        }
    }

    private JWTpayload getCurrentPayload(HttpServletRequest request) {
        return (JWTpayload) request.getAttribute("JWTpayload");
    }
}