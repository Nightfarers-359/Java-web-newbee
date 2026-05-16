package com.project.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.platform.DTO.ChangePasswordDTO;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.entity.User;
import com.project.platform.mapper.UserMapper;
import com.project.platform.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    enum Role {
        USER,
        MERCHANT,
        ADMIN
    }

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public User login(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = this.getOne(queryWrapper);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            if (user.getIsBanned()) {
                throw new RuntimeException("用户被封禁");
            }
            return user;
        }

        return null;
    }

    @Override
    public boolean registerWithDTO(RegisterRequestDTO registerRequestDTO) {
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, registerRequestDTO.getUsername());

        long count = this.count(queryWrapper);
        if (count > 0) {
            return false;
        }

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword())); // 使用BCrypt加密密码
        user.setEmail(registerRequestDTO.getEmail());
        user.setNickname(registerRequestDTO.getNickname());
        user.setPhone(registerRequestDTO.getPhone());
        user.setRole("user"); // 默认角色
        user.setIsBanned(false);
        user.setCreatedAt(new Date());

        return this.save(user);
    }

    @Transactional(rollbackFor = Exception.class) // 避免出错后发生数据库不同步的问题
    @Override
    public boolean changePassword(ChangePasswordDTO dto) {
        String username = getCurrentUsername();
        User user = this.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户 '" + username + "' 不存在");
        }

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("旧密码错误");
        }

        String encodedNewPassword = passwordEncoder.encode(dto.getNewPassword());
        user.setPassword(encodedNewPassword);

        // 使用ServiceImpl提供的updateById方法，其返回值为boolean
        return this.updateById(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // 在jwt filter里面，传进来的实际上是userId
        User user = this.getUserById(Long.parseLong(username));
        if (user == null) {
            throw new UsernameNotFoundException(
                    "User not found with id: " + username);
        }
        if (user.getIsBanned()) {
            throw new UsernameNotFoundException(
                    "User with id: " + username + " is banned.");
        }

        // Create authority list from user's role
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole() != null && !user.getRole().trim().isEmpty()) {
            // SpringSecurity的 `hasRole("ADMIN")` 需要名为 "ROLE_ADMIN"的身份.
            // 所以这里转换一下
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    @Override
    public User getUserByName(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return this.getOne(queryWrapper);
    }

    @Override
    public User getUserById(Long id) {
        return this.getById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return this.getOne(queryWrapper);
    }

    @Override
    public User getUserByPhone(String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean grantAdminRole(Long userId) {
        User user = this.getById(userId);
        if (user != null) {
            user.setRole("admin");
            return this.updateById(user);
        }
        return false;
    }

    @Override
    public boolean banUser(Long userId) {
        User user = this.getById(userId);
        if (user != null) {
            user.setIsBanned(true);
            return this.updateById(user);
        }
        return false;
    }

    @Override
    public boolean unbanUser(Long userId) {
        User user = this.getById(userId);
        if (user != null) {
            user.setIsBanned(false);
            return this.updateById(user);
        }
        return false;
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) { // 如果这里的主体是代表用户的对象
            return ((UserDetails) principal).getUsername();
        }
        if (principal instanceof String) {
            return (String) principal;
        }
        throw new RuntimeException("无法获取当前用户信息");
    }

}