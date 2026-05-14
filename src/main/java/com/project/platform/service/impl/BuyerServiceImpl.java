package com.project.platform.service.impl;



import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.platform.DTO.CommentRequestDTO;
import com.project.platform.DTO.CommentResponseDTO;
import com.project.platform.DTO.OrderRequestDTO;
import com.project.platform.DTO.OrderResponseDTO;
import com.project.platform.DTO.PaymentRequestDTO;
import com.project.platform.entity.Comment;
import com.project.platform.entity.Order;
import com.project.platform.mapper.CommentMapper;
import com.project.platform.mapper.OrderMapper;
import com.project.platform.service.BuyerService;

import jakarta.annotation.Resource;

/**
 * <p>
 * 订单服务实现类
 * 实现了 SellerService 接口中的所有业务逻辑
 * </p>
 *
 * @author YourName
 * @since 2026-05-14
 */
@Service 
public class BuyerServiceImpl extends ServiceImpl<OrderMapper, Order> implements BuyerService {

    /**
     * 注入 OrderMapper，用于操作订单表
     */
    @Resource
    private OrderMapper orderMapper;

    /**
     * 注入 CommentMapper，用于操作评论表
     */
    @Resource
    private CommentMapper commentMapper;

    /**
     * 新增订单
     * 将 DTO 转换为 Entity 并插入数据库
     *
     * @param orderDTO 前端传入的订单数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 开启事务，发生异常时回滚
    public void addOrder(OrderRequestDTO orderDTO) {
        Order order = new Order();
        
        BeanUtils.copyProperties(orderDTO,order);
        
        order.setPaid(false); // 默认未支付
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());

        orderMapper.insert(order);
    }

    /**
     * 根据 ID 取消订单
     * 实际操作是更新订单状态为“已取消”
     *
     * @param id 订单ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrderById(Integer id) {
        Order order = orderMapper.selectById(id);
        
        if (order == null) {
            throw new IllegalArgumentException("订单不存在，ID: " + id);
        }

        order.setPaid(true);
        order.setUpdatedAt(new Date());
        
        orderMapper.updateById(order);
    }

    /**
     * 根据 ID 查询订单详情
     * 将 Entity 转换为 DTO 返回给前端
     *
     * @param id 订单ID
     * @return OrderResponseDTO
     */
    @Override
    public OrderResponseDTO getOrderById(Integer id) {
        Order order = orderMapper.selectById(id);
        
        if (order == null) {
            return null;
        }

        OrderResponseDTO responseDTO = new OrderResponseDTO();
        BeanUtils.copyProperties(order, responseDTO);
        
        
        return responseDTO;
    }

    /**
     * 评论商品
     * 将评论 DTO 保存到数据库
     *
     * @param commentDTO 评论数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void commentItem(CommentRequestDTO commentDTO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        
        comment.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
        
        commentMapper.insert(comment);
    }

    /**
     * 根据 ID 获取评论详情
     *
     * @param id 评论ID
     * @return CommentResponseDTO
     */
    @Override
    public CommentResponseDTO getCommentByid(Integer id) {
        Comment comment = commentMapper.selectById(id);
        
        if (comment == null) {
            return null;
        }

        CommentResponseDTO responseDTO = new CommentResponseDTO();
        BeanUtils.copyProperties(comment, responseDTO);
        
        return responseDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(PaymentRequestDTO paymentDTO) {
        Integer orderId = paymentDTO.getOrderId();
        Double payAmount = paymentDTO.getPayAmount();

        // 根据 ID 查询订单
        Order order = orderMapper.selectById(orderId);
        
        // 校验订单是否存在
        Assert.notNull(order, "订单不存在，ID: " + orderId);

        // 校验订单是否已支付 (防止重复支付)
        if (Boolean.TRUE.equals(order.isPaid())) {
            throw new IllegalStateException("订单已支付，请勿重复操作");
        }
        //确认金额
        Double orderPrice = order.getTotalPrice();
        if (payAmount.compareTo(orderPrice) != 0) {
            throw new IllegalArgumentException("支付金额与订单金额不符");
        }

        // 更新订单状态为已支付
        order.setPaid(true);
        order.setUpdatedAt(new Date()); // 更新时间
        
        // 执行更新
        orderMapper.updateById(order);
    }
}