package com.secondhand.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RefundRecord {
    private Long refundId;
    private Long orderId;
    private Long userId;
    private String refundReason;
    private String refundDesc;
    private String refundImage;
    private Integer refundStatus;
    private BigDecimal refundAmount;
    private LocalDateTime handleTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}