package com.project.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.platform.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
