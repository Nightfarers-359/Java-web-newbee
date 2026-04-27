package com.project.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@TableName("Comments")
public class Comments {
    @Getter
    @Setter
    private int item_id;
    @Getter
    @Setter
    private int user_buyer_id;
}
