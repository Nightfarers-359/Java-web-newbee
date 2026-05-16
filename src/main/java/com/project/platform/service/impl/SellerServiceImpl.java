package com.project.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.platform.DTO.ItemRequestDTO;
import com.project.platform.DTO.ItemResponseDTO;
import com.project.platform.DTO.SellerCommentResponseDTO;
import com.project.platform.DTO.JWTpayload;
import com.project.platform.entity.Comment;
import com.project.platform.entity.Item;
import com.project.platform.mapper.CommentMapper;
import com.project.platform.mapper.ItemMapper;
import com.project.platform.service.SellerService;
import com.project.platform.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional
    public void addItem(ItemRequestDTO itemDTO, Integer sellerId) {
        Item item=new Item();
        BeanUtils.copyProperties(itemDTO, item);
        item.setOwnerId(sellerId);
        item.setHidden(false); // 默认不隐藏
        item.setCreatedAt(new Date());
        item.setUpdatedAt(new Date());
        itemMapper.insert(item);
    }

    @Override
    @Transactional
    public void updateItem(ItemRequestDTO itemDTO, Integer sellerId) {
        // 检查商品是否存在且属于当前卖家
        Item item=itemMapper.selectById(itemDTO.getId());
        if (item==null || !item.getOwnerId().equals(sellerId)) {
            throw new RuntimeException("商品不存在或无权限修改");
        }
        // 只更新允许修改的字段
        BeanUtils.copyProperties(itemDTO, item, "id", "ownerId", "createdAt");
        item.setUpdatedAt(new Date());
        itemMapper.updateById(item);
    }

    @Override
    @Transactional
    public void deleteItem(Integer itemId, Integer sellerId) {
        Item item=itemMapper.selectById(itemId);
        if (item==null || !item.getOwnerId().equals(sellerId)) {
            throw new RuntimeException("商品不存在或无权限删除");
        }
        // 软删除：设置deleted_at字段（Item实体中需有deletedAt）
        item.setDeletedAt(new Date());
        itemMapper.updateById(item);
        // 若需硬删除：itemMapper.deleteById(itemId);
    }

    @Override
    public Page<ItemResponseDTO> getMyItems(Integer sellerId, int page, int size, String category) {
        Page<Item> pageParam=new Page<>(page, size);
        LambdaQueryWrapper<Item> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Item::getOwnerId, sellerId)
                .eq(Item::getDeletedAt, null) // 未删除
                .orderByDesc(Item::getCreatedAt);
        if (category!=null && !category.isEmpty()) {
            wrapper.eq(Item::getCategory, category);
        }
        Page<Item> itemPage=itemMapper.selectPage(pageParam, wrapper);
        // 转换为DTO分页对象
        Page<ItemResponseDTO> dtoPage=new Page<>(itemPage.getCurrent(), itemPage.getSize(), itemPage.getTotal());
        List<ItemResponseDTO> dtoList=itemPage.getRecords().stream()
                .map(ItemResponseDTO::new)
                .collect(Collectors.toList());
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

    @Override
    public List<SellerCommentResponseDTO> getCommentsByItemId(Integer itemId, Integer sellerId) {
        // 校验商品属于当前卖家
        Item item=itemMapper.selectById(itemId);
        if (item==null || !item.getOwnerId().equals(sellerId)) {
            throw new RuntimeException("商品不存在或无权限查看评论");
        }
        // 查询该商品下的所有评论（未隐藏的或全部——可根据需求调整）
        LambdaQueryWrapper<Comment> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getItemId, itemId)
                .eq(Comment::getDeletedAt, null) // 如果 Comment 有软删除字段
                .orderByDesc(Comment::getCreatedAt);
        List<Comment> comments=commentMapper.selectList(wrapper);
        // 组装 DTO，需要用户名。为了简单，这里只返回评论内容和用户ID
        // 若需用户名，可联查 User 表，这里简化（实际项目中应完善）
        List<SellerCommentResponseDTO> result=new ArrayList<>();
        for (Comment comment : comments) {
            SellerCommentResponseDTO dto=new SellerCommentResponseDTO(
                    comment.getId(),
                    comment.getItemId(),
                    item.getName(),
                    comment.getUserId(),
                    "用户" + comment.getUserId(), // 暂代用户名
                    comment.getContent(),
                    comment.isHidden(),
                    comment.getCreatedAt()
            );
            result.add(dto);
        }
        return result;
    }

    @Override
    public Integer getUserIdFromToken(String token) {
        JWTpayload payload=jwtUtil.verifyToken(token);
        return payload.getId(); // payload.getId()是int，自动转Integer
    }
}