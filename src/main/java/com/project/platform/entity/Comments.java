package com.project.platform.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("Comments")
public class Comments {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("item_id")
    private int itemId;
    @TableField("user_id")
    private int userId;
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
