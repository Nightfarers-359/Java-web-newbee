package com.project.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.platform.entity.User;
import com.project.platform.mapper.UserMapper;
import com.project.platform.service.UserService;
import com.project.platform.DTO.RegisterRequestDTO;
import org.springframework.stereotype.Service;
import java.util.Date;

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
        // TODO
        return null;
    }

    @Override
    public User getUserById(Long id) {
        // TODO
        return null;
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