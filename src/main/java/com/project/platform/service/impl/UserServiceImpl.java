package com.project.platform.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.platform.DTO.JWTpayload;
import com.project.platform.DTO.LoginRequestDTO;
import com.project.platform.DTO.LoginResponseDTO;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.DTO.UpdateUserDTO;
import com.project.platform.DTO.UserResponseDTO;
import com.project.platform.entity.User;
import com.project.platform.mapper.UserMapper;
import com.project.platform.service.UserService;
import com.project.platform.util.JwtUtil;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil; // 注入JwtUtil


    //注册
    @Override
    public UserResponseDTO register(RegisterRequestDTO registerRequest) {
        //检查唯一性
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", registerRequest.getUsername())
                   .or().eq("email", registerRequest.getEmail());
        if (getOne(queryWrapper) != null) {
            throw new RuntimeException("用户名或邮箱已存在");
        }

        //构建用户实体
        User user = new User();
        //DTO转换成Entity
        BeanUtils.copyProperties(registerRequest, user);
        //密码加密
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        //设置默认值
        user.setRole("USER"); // 默认普通用户
        user.setIsBanned(false);
        user.setCreatedAt(new Date());

        save(user);

        return new UserResponseDTO(user);
    }


    //登录
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        //查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginRequest.getEmailorname())
                   .or().eq("email", loginRequest.getEmailorname());
        User user = getOne(queryWrapper);

        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        //校验密码
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        //生成 Token(jwt,JWTpayload)
        JWTpayload payload = new JWTpayload();
        payload.setId(user.getId());
        payload.setAdmin(Objects.equals(user.getRole(), "ADMIN")); // Role 为 "ADMIN" 字符串
        
        String token = jwtUtil.createToken(payload);

        //构建响应
        UserResponseDTO userInfo = new UserResponseDTO(user);
        return new LoginResponseDTO(token, userInfo);
    }

    @Override
    public UserResponseDTO getUserInfoById(Long id) {
        User user = getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return new UserResponseDTO(user);
    }


    //退出登录
    @Override
    public void logout(String token) {
        // TODO
    }


    //更新用户信息
    @Override
public UserResponseDTO updateUserInfo(Long id, UpdateUserDTO updateRequest) {
    //先查用户
    User user = this.getById(id);
    if (user == null) {
        throw new RuntimeException("用户不存在，无法更新");
    }

    //检查更新后的用户名或邮箱是否与其他用户冲突
    if (updateRequest.getUsername() != null || updateRequest.getEmail() != null) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 查询是否有其他用户使用了这个新用户名或新邮箱
        queryWrapper.and(wrapper -> 
            wrapper.eq("username", updateRequest.getUsername())
                   .or()
                   .eq("email", updateRequest.getEmail())
        ).ne("id", id); // 排除当前用户自己
        
        if (getOne(queryWrapper) != null) {
            throw new RuntimeException("用户名或邮箱已被其他用户占用");
        }
    }

    //只拷贝非空字段
    //获取 updateRequest 中值为 null 的字段名
    BeanWrapper src = new BeanWrapperImpl(updateRequest);
    Set<String> nullPropertyNames = new HashSet<>();
    for (var pd : src.getPropertyDescriptors()) {
        if (src.getPropertyValue(pd.getName()) == null) {
            nullPropertyNames.add(pd.getName());
        }
    }
    // 将非空字段从 updateRequest 拷贝到 user 实体中
    // 参数含义：(源对象, 目标对象, 需要忽略的属性名数组)
    BeanUtils.copyProperties(updateRequest, user, nullPropertyNames.toArray(new String[0]));

    this.updateById(user);

    return new UserResponseDTO(user);
}
}