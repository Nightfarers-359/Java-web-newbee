package com.project.platform.service;

import com.project.platform.entity.Comment;
import com.project.platform.entity.Order;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
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
