package com.project.platform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.platform.entity.Comment;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
