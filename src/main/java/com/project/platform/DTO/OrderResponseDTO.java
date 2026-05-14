package com.project.platform.DTO;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private Integer id;
    private Integer buyerId;
    private Integer sellerId;
    private Integer itemId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Boolean isPaid;
    private Date createTime;
    private Date updateTime;
}
