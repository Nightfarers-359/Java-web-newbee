package com.project.platform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.platform.entity.Order;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
}
