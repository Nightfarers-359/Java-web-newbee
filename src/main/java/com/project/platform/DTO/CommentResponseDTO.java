package com.project.platform.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class CommentResponseDTO {
    private Integer id;
    private Integer itemId;
    private Integer userId;
    private String content;
    private Boolean isHidden;
    private Date createdAt;
}
