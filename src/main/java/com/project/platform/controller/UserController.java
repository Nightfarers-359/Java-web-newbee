package com.project.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.platform.DTO.ChangePasswordDTO;
import com.project.platform.entity.User;
import com.project.platform.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
