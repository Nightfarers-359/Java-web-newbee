package com.project.platform.DTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    
    @NotNull(message = "订单ID不能为空")
    private Integer orderId;


    @NotNull(message = "支付金额不能为空")
    @Min(value = 0, message = "金额不能小于0")
    private Double payAmount;
}