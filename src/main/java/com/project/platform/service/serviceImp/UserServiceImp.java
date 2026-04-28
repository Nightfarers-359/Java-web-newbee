package com.project.platform.service.serviceImp;

import org.springframework.stereotype.Service;

import com.project.platform.entity.Users;
import com.project.platform.mapper.UserMapper;
import com.project.platform.service.UserService;

@Service
public class UserServiceImp implements UserService {
    private final UserMapper userMapper;

    public UserServiceImp(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Users getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public Users getUserByEmail(String email) {
        return userMapper.selectById(email);
    }

    @Override
    public Users getUserByPhone(String phone) {
        return userMapper.selectById(phone);
    }

    @Override
    public Users getUserByName(String name) {
        return userMapper.selectById(name);
    }

}
