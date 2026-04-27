package com.project.platform.controller;

import com.project.platform.entity.Comments;
import com.project.platform.entity.Orders;
import com.project.platform.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller/orderlist")
public class seller {
    @Autowired
    SellerService sellerService;

    @PostMapping
    public void addOrderList(@RequestBody Orders order){
        sellerService.addOrderList(order);
    }
    @PostMapping
    public void cancelOrderList(@RequestBody Orders order){
        sellerService.cancelOrderList(order);
    }
    @GetMapping("/{id}")
    public Orders getOrderListByid(@PathVariable Integer id){
        return sellerService.getOrderListByid(id);
    }
    @GetMapping("/{userid}")
    public List<Orders> getOrderListByids(@PathVariable Long userid){
        return sellerService.getOrderListByids(userid);
    }
    @PostMapping
    public void commentItem(@RequestBody Comments comment){
        sellerService.commentItem(comment);
    }
}
