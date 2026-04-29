package com.project.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.platform.entity.Comments;
import com.project.platform.entity.Orders;
import com.project.platform.mapper.CommentsMapper;
import com.project.platform.mapper.OrdersMapper;
import com.project.platform.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public void addOrderList(Orders order){
        ordersMapper.insert(order);
    }
    @Override
    public void cancelOrderList(Orders order){
        ordersMapper.deleteById(order.getId());
    }

    @Override
    public Orders getOrderListByid(Integer id){
        return ordersMapper.selectById(id);
    }

    @Override
    public List<Orders> getOrderListByids(Long userid){
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userid);

        return ordersMapper.selectList(wrapper);
    }
    @Override
    public void commentItem(Comments comment){
        commentsMapper.insert(comment);
    }
}
