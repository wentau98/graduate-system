package com.secondhand.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderComment {
    private Long commentId;
    private Long orderId;
    private Long productId;
    private Long userId;
    private Long sellerId;
    private Integer score;
    private String content;
    private LocalDateTime createTime;
}