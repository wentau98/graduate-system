package com.secondhand.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductCollection {
    private Long collectId;
    private Long productId;
    private Long userId;
    private LocalDateTime collectTime;
}