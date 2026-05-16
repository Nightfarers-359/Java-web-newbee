package com.project.platform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.platform.DTO.ItemRequestDTO;
import com.project.platform.DTO.ItemResponseDTO;
import com.project.platform.DTO.SellerCommentResponseDTO;
import com.project.platform.common.Result;
import com.project.platform.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/merchant")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PostMapping("/item")
    public Result addItem(@Valid @RequestBody ItemRequestDTO itemDTO,
                          @RequestHeader("Authorization") String authHeader) {
        Integer sellerId=getSellerIdFromAuth(authHeader);
        sellerService.addItem(itemDTO, sellerId);
        return Result.success("商品添加成功");
    }

    @PutMapping("/item/{id}")
    public Result updateItem(@PathVariable Integer id,
                             @Valid @RequestBody ItemRequestDTO itemDTO,
                             @RequestHeader("Authorization") String authHeader) {
        Integer sellerId=getSellerIdFromAuth(authHeader);
        itemDTO.setId(id);
        sellerService.updateItem(itemDTO, sellerId);
        return Result.success("商品修改成功");
    }

    @DeleteMapping("/item/{id}")
    public Result deleteItem(@PathVariable Integer id,
                             @RequestHeader("Authorization") String authHeader) {
        Integer sellerId=getSellerIdFromAuth(authHeader);
        sellerService.deleteItem(id, sellerId);
        return Result.success("商品删除成功");
    }

    @GetMapping("/items")
    public Result<Page<ItemResponseDTO>> getMyItems(
            @RequestParam(defaultValue="1") int page,
            @RequestParam(defaultValue="10") int size,
            @RequestParam(required=false) String category,
            @RequestHeader("Authorization") String authHeader) {
        Integer sellerId=getSellerIdFromAuth(authHeader);
        Page<ItemResponseDTO> pageResult=sellerService.getMyItems(sellerId, page, size, category);
        return Result.success("查询成功", pageResult);
    }

    @GetMapping("/item/{itemId}/comments")
    public Result<List<SellerCommentResponseDTO>> getCommentsByItem(
            @PathVariable Integer itemId,
            @RequestHeader("Authorization") String authHeader) {
        Integer sellerId=getSellerIdFromAuth(authHeader);
        List<SellerCommentResponseDTO> comments=sellerService.getCommentsByItemId(itemId, sellerId);
        return Result.success("查询成功", comments);
    }

    private Integer getSellerIdFromAuth(String authHeader) {
        if (authHeader==null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("未提供有效的认证信息");
        }
        String token=authHeader.substring(7);
        return sellerService.getUserIdFromToken(token);
    }
}
