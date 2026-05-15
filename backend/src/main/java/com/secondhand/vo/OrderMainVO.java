package com.secondhand.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderMainVO {

    // ====================== OrderMain 字段 ======================
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

    // ====================== ProductImage 字段 ======================
    private Long imageId;
    private String imageUrl;
    private Integer imageSort;
}