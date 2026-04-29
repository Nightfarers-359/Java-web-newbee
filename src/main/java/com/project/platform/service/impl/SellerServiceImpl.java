package com.project.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.platform.entity.Comment;
import com.project.platform.entity.Order;
import com.project.platform.mapper.CommentMapper;
import com.project.platform.mapper.OrderMapper;
import com.project.platform.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void addOrderList(Order order){
        orderMapper.insert(order);
    }
    @Override
    public void cancelOrderList(Order order){
        orderMapper.deleteById(order.getId());
    }

    @Override
    public Order getOrderListByid(Integer id){
        return orderMapper.selectById(id);
    }

    @Override
    public List<Order> getOrderListByids(Long userid){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userid);

        return orderMapper.selectList(wrapper);
    }
    @Override
    public void commentItem(Comment comment){
        commentMapper.insert(comment);
    }
}
