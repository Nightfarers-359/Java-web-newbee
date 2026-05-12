package com.project.platform.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("Comments")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("item_id")
    private Integer itemId;
    @TableField("user_id")
    private Integer userId;
    private String content;
    @TableField("is_hidden")
    private boolean isHidden;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("deleted_at")
    private Date deletedAt;
}
