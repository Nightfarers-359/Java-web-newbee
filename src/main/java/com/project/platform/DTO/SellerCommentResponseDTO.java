package com.project.platform.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class SellerCommentResponseDTO {
    private Integer commentId;
    private Integer itemId;
    private String itemName;
    private Integer userId;
    private String username;
    private String content;
    private Boolean isHidden;
    private Date createdAt;

    public SellerCommentResponseDTO(Integer commentId, Integer itemId, String itemName,
                                    Integer userId, String username, String content,
                                    Boolean isHidden, Date createdAt) {
        this.commentId=commentId;
        this.itemId=itemId;
        this.itemName=itemName;
        this.userId=userId;
        this.username=username;
        this.content=content;
        this.isHidden=isHidden;
        this.createdAt=createdAt;
    }
}