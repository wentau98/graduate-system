package com.secondhand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)//允许new OrderMain().setOrderId(orderId).setOrderStatus(2)并且能返回orderMain
public class OrderMain {
    @TableId(type = IdType.AUTO)
    private Long orderId;
    private String orderNo;
    private Long userId;
    private Long sellerId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer orderStatus;
    private Integer payType;
    private LocalDateTime payTime;
    private Integer deliveryType;
    private String deliveryNo;
    private LocalDateTime receiveTime;
    private LocalDateTime cancelTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}