package com.project.platform.controller;

import com.project.platform.DTO.JWTpayload;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.entity.User;
import com.project.platform.service.UserService;
import com.project.platform.util.JwtUtil;
import com.project.platform.util.MailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MailService mailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
            @RequestParam String password) {
        User user = userService.login(username, password);
        if (user != null) {
            JWTpayload payload = new JWTpayload();
            // UserId是个Long, 但是jwtpayload要求int
            // 可能需要改
            payload.setId(user.getId().intValue());
            payload.setUsername(user.getUsername());
            // 临时处理职责，后续解决
            payload.setAdmin("admin".equalsIgnoreCase(user.getRole()));

            String token = jwtUtil.createToken(payload);

            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(401).body("User name or password error.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        boolean success = userService.registerWithDTO(registerRequestDTO);
        if (success) {
            return ResponseEntity.ok("用户注册成功！");
        } else {
            return ResponseEntity.badRequest().body("用户名已存在");
        }
    }

    // @GetMapping("/logout")
    // public String logout() {
    // return "logout";
    // }
    // 客户端本地删除token即可，后续可以考虑添加禁用Refresh Token的功能

    @PostMapping("/admin/grant")
    public ResponseEntity<?> grantAdminRole(@RequestParam Long userId) {
        boolean success = userService.grantAdminRole(userId);
        if (success) {
            return ResponseEntity.ok("User " + userId + " has been granted admin role.");
        } else {
            return ResponseEntity.badRequest().body("User not found or already an admin.");
        }
    }

    @PostMapping("/admin/ban/{userId}")
    public ResponseEntity<?> banUser(@PathVariable Long userId) {
        boolean success = userService.banUser(userId);
        if (success) {
            return ResponseEntity.ok("User " + userId + " has been banned.");
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }

    @PostMapping("/admin/unban/{userId}")
    public ResponseEntity<?> unbanUser(@PathVariable Long userId) {
        boolean success = userService.unbanUser(userId);
        if (success) {
            return ResponseEntity.ok("User " + userId + " has been unbanned.");
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }

    // @GetMapping("/refresh")
    // public String refresh() {
    // return "refresh";
    // }
    // 同上，目前没添加Refresh Token

    // @GetMapping("/changePassword")
    // public String changePassword() {
    // return "changePassword";
    // }
    // 已在UserController中实现

    @GetMapping("/sendmailtest")
    public String sendmailTest() {
        mailService.sendMailAsync("xxx@qq.com", "test", "test");
        return "sendmailTest";
    }
}
