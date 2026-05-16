package com.project.platform.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemRequestDTO {
    private Integer id; // 更新时需要传入

    @NotBlank(message="商品名称不能为空")
    private String name;

    private String description;

    @NotNull(message="价格不能为空")
    @Positive(message="价格必须大于0")
    private Double price;

    @NotBlank(message="分类不能为空")
    private String category;

    private String imageUrl;
}