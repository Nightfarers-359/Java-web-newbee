package com.project.platform.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequestDTO {

    @NotNull(message = "商品ID不能为空")
    private Integer itemId;

    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @NotBlank(message = "评论内容不能为空")
    private String content;
}