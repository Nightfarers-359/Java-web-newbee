package com.project.platform.service;

import com.project.platform.entity.Comments;
import com.project.platform.entity.Orders;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SellerService {
    void addOrderList(Orders order);
    void cancelOrderList(Orders order);;
    Orders getOrderListByid(Integer id);
    List<Orders> getOrderListByids(Long userid);
    void commentItem(Comments comment);

}
