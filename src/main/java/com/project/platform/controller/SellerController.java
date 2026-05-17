package com.project.platform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.platform.DTO.ItemRequestDTO;
import com.project.platform.DTO.ItemResponseDTO;
import com.project.platform.DTO.SellerCommentResponseDTO;
import com.project.platform.common.Result;
import com.project.platform.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/merchant")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    // 修改addItem方法，增加BindingResult校验
    @PostMapping("/item/add")
    public Result<String> addItem(@Valid @RequestBody ItemRequestDTO itemDTO,
                          BindingResult bindingResult,
                          @RequestHeader("Authorization") String authHeader) {
        // 拦截DTO校验错误
        if (bindingResult.hasErrors()) {
            String errorMsg=bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(";"));
            return Result.error(errorMsg);
        }
        Integer sellerId=getSellerIdFromAuth(authHeader);
        sellerService.addItem(itemDTO, sellerId);
        return Result.success("商品添加成功");
    }

    // 修改updateItem方法，增加BindingResult校验及id合法性检查
    @PutMapping("/item/update/{id}")
    public Result<String> updateItem(@PathVariable Integer id,
                             @Valid @RequestBody ItemRequestDTO itemDTO,
                             BindingResult bindingResult,
                             @RequestHeader("Authorization") String authHeader) {
        // 拦截DTO校验错误
        if (bindingResult.hasErrors()) {
            String errorMsg=bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(";"));
            return Result.error(errorMsg);
        }
        // 校验路径参数 id
        if (id==null||id<=0) {
            return Result.error("无效的商品ID");
        }
        Integer sellerId=getSellerIdFromAuth(authHeader);
        itemDTO.setId(id);
        sellerService.updateItem(itemDTO, sellerId);
        return Result.success("商品修改成功");
    }

    @DeleteMapping("/item/delete/{id}")
    public Result<String> deleteItem(@PathVariable Integer id,
                             @RequestHeader("Authorization") String authHeader) {
        Integer sellerId=getSellerIdFromAuth(authHeader);
        sellerService.deleteItem(id, sellerId);
        return Result.success("商品删除成功");
    }

    @GetMapping("/item/get")
    public Result<Page<ItemResponseDTO>> getMyItems(
            @RequestParam(defaultValue="1") int page,
            @RequestParam(defaultValue="10") int size,
            @RequestParam(required=false) String category,
            @RequestHeader("Authorization") String authHeader) {
        Integer sellerId=getSellerIdFromAuth(authHeader);
        Page<ItemResponseDTO> pageResult=sellerService.getMyItems(sellerId, page, size, category);
        return Result.success("查询成功", pageResult);
    }

    @GetMapping("/item/comments/get/{id}")
    public Result<List<SellerCommentResponseDTO>> getCommentsByItem(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String authHeader) {
        Integer sellerId=getSellerIdFromAuth(authHeader);
        List<SellerCommentResponseDTO> comments=sellerService.getCommentsByItemId(id, sellerId);
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
