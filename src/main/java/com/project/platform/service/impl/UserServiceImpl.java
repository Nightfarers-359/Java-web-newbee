package com.project.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.entity.User;
import com.project.platform.mapper.UserMapper;
import com.project.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User login(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = this.getOne(queryWrapper);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
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

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // 在jwt filter里面，传进来的实际上是userId
        User user = this.getUserById(Long.parseLong(username));
        if (user == null) {
            throw new UsernameNotFoundException(
                    "User not found with id: " + username);
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
        // TODO
        return null;
    }

    @Override
    public User getUserByPhone(String phone) {
        // TODO
        return null;
    }

}