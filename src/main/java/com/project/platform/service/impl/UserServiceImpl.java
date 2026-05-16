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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.platform.DTO.ChangePasswordDTO;
import com.project.platform.DTO.RegisterRequestDTO;
import com.project.platform.DTO.UpdateUserDTO;
import com.project.platform.DTO.UserResponseDTO;
import com.project.platform.entity.User;
import com.project.platform.mapper.UserMapper;
import com.project.platform.service.UserService;
import com.project.platform.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public UserResponseDTO getUserInfoById(Long id) {
        User user = getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return new UserResponseDTO(user);
      
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
      
      
      
    //更新用户信息
    @Override
public UserResponseDTO updateUserInfo(Long id, UpdateUserDTO updateRequest) {
    //先查用户
    User user = this.getById(id);
    if (user == null) {
        throw new RuntimeException("用户不存在，无法更新");
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