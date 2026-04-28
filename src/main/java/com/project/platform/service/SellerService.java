package com.project.platform.service;

import java.util.List;

import com.project.platform.entity.Comments;
import com.project.platform.entity.Orders;

public interface SellerService {
    void addOrderList(Orders order);
    void cancelOrderList(Orders order);;
    Orders getOrderListByid(Integer id);
    List<Orders> getOrderListByids(Long userid);
    void commentItem(Comments comment);

}
