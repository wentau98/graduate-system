package com.secondhand.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    // 订单ID
    private Long orderId;
    // 订单编号
    private String orderNo;
    // 用户ID（买家）
    private Long userId;
    // 卖家ID
    private Long sellerId;
    // 商品ID
    private Long productId;
    // 商品名称
    private String productName;
    // 商品价格
    private BigDecimal productPrice;
    // 订单状态：1-待付款 2-待发货 3-待收货 4-已完成 5-已取消
    private Integer orderStatus;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 数据有效性（遵循你的规范：不设非空）
    private Integer dataValid;

//    private int payStatus;

}