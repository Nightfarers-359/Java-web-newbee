package com.project.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.platform.DTO.CommentRequestDTO;
import com.project.platform.DTO.OrderRequestDTO;
import com.project.platform.DTO.OrderResponseDTO;
import com.project.platform.DTO.PaymentRequestDTO;
import com.project.platform.common.Result;
import com.project.platform.service.BuyerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/order")
public class BuyerController {

    @Autowired
    private BuyerService sellerService;

    /**
     * 新增订单
     * POST /order/add
     */
    @PostMapping("/add")
    public Result addOrder(@Valid @RequestBody OrderRequestDTO orderDTO) {
        sellerService.addOrder(orderDTO);
        return Result.success("订单创建成功");
    }

    /**
     * 取消订单
     * POST /order/cancel/{id}
     */
    @PostMapping("/cancel/{id}")
    public Result cancelOrder(@PathVariable Integer id) {
        sellerService.cancelOrderById(id);
        return Result.success("订单取消成功");
    }

    /**
     * 根据 ID 查询订单详情
     * GET /order/{id}
     */
    @GetMapping("/{id}")
    public Result getOrderById(@PathVariable Integer id) {
        OrderResponseDTO order = sellerService.getOrderById(id);
        if (order == null) {
            return Result.error("订单不存在");
        }
        return Result.success("获取订单列表成功",order);
    }

    /**
     * 支付订单
     * POST /order/pay
     */
    @PostMapping("/pay")
    public Result payOrder(@Valid @RequestBody PaymentRequestDTO paymentDTO) {
        sellerService.payOrder(paymentDTO);
        return Result.success("支付成功");
    }

    /**
     * 评论商品
     * POST /order/comment
     */
    @PostMapping("/comment")
    public Result commentItem(@Valid @RequestBody CommentRequestDTO commentDTO) {
        sellerService.commentItem(commentDTO);
        return Result.success("评论成功");
    }
}