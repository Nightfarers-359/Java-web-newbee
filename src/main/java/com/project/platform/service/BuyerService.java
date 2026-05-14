package com.project.platform.service;

import com.project.platform.DTO.CommentRequestDTO;
import com.project.platform.DTO.CommentResponseDTO;
import com.project.platform.DTO.OrderRequestDTO;
import com.project.platform.DTO.OrderResponseDTO;
import com.project.platform.DTO.PaymentRequestDTO;
/**
 * 订单处理接口
 * 定义订单的添加、支付、删除、查询、评论、查找评论
 */
public interface BuyerService {
    /**
     * @param orderDTO
     */
    void addOrder(OrderRequestDTO orderDTO);
    /**
     * @param id
     */
    void cancelOrderById(Integer id);
    /**
     * 支付订单
     * @param paymentDTO 支付请求参数（包含订单ID和支付金额等）
     */
    void payOrder(PaymentRequestDTO paymentDTO);
    /**
     * 
     * @param id
     * @return
     */
    OrderResponseDTO getOrderById(Integer id);
    /**
     * 
     * @param commentDTO
     */
    void commentItem(CommentRequestDTO commentDTO);
    /**
     * 
     * @param id
     * @return
     */
    CommentResponseDTO getCommentByid(Integer id);
}
