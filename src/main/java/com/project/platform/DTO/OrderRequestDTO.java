package com.project.platform.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {

    @NotNull(message = "商品ID不能为空")
    private Integer itemId;

    @NotNull(message = "购买数量不能为空")
    @Min(value = 1, message = "数量至少为1")
    private Integer quantity;

    @NotNull(message = "总价不能为空")
    private Double totalPrice;

    @NotNull(message = "买家ID不能为空")
    private Integer buyerId;

    @NotNull(message="卖家ID不能为空")
    private Integer sellerId;
}