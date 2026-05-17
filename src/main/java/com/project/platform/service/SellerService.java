package com.project.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.platform.DTO.ItemRequestDTO;
import com.project.platform.DTO.ItemResponseDTO;
import com.project.platform.DTO.SellerCommentResponseDTO;

import java.util.List;

public interface SellerService {
    void addItem(ItemRequestDTO itemDTO, Integer sellerId);
    void updateItem(ItemRequestDTO itemDTO, Integer sellerId);
    void deleteItem(Integer itemId, Integer sellerId);
    Page<ItemResponseDTO> getMyItems(Integer sellerId, int page, int size, String category);
    List<SellerCommentResponseDTO> getCommentsByItemId(Integer itemId, Integer sellerId);
    Integer getUserIdFromToken(String token);
}