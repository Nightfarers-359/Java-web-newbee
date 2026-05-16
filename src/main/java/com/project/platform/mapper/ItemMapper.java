package com.project.platform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.platform.entity.Item;
@Mapper
public interface ItemMapper extends BaseMapper<Item> {
    
}
