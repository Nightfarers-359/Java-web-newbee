package com.project.platform.service;

import java.util.List;

import com.project.platform.entity.Comment;
import com.project.platform.entity.Order;

public interface SellerService {
    void addOrderList(Order order);
    void cancelOrderList(Order order);;
    Order getOrderListByid(Integer id);
    List<Order> getOrderListByids(Long userid);
    void commentItem(Comment comment);

}
