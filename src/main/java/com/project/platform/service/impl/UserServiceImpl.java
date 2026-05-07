package com.project.platform.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.entity.User;
import com.project.platform.mapper.UserMapper;
import com.project.platform.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Override
    public User login(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username)
                .eq(User::getPassword, password);

        return this.getOne(queryWrapper);
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
        user.setPassword(registerRequestDTO.getPassword()); // 实际应用中应该进行加密
        user.setEmail(registerRequestDTO.getEmail());
        user.setNickname(registerRequestDTO.getNickname());
        user.setPhone(registerRequestDTO.getPhone());
        user.setRole("user"); // 默认角色
        user.setIsBanned(false);
        user.setCreatedAt(new Date());

        return this.save(user);
    }

    @Override
    public User getUserByName(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return this.getOne(wrapper);
    }

    @Override
    public User getUserById(Long id) {
        return this.getById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return this.getOne(wrapper);
    }

    @Override
    public User getUserByPhone(String phone) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        return this.getOne(wrapper);
    }

}