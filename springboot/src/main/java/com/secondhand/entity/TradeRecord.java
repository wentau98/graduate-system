package com.secondhand.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TradeRecord {
    private Long recordId;
    private Long orderId;
    private Long userId;
    private Integer tradeType;
    private BigDecimal amount;
    private Integer tradeStatus;
    private LocalDateTime tradeTime;
}