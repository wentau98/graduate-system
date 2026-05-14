package com.secondhand.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductImage {
    private Long imageId;
    private Long productId;
    private String imageUrl;
    private Integer imageSort;
    private LocalDateTime createTime;
    public ProductImage(Long productId, String imageUrl, Integer imageSort, LocalDateTime createTime) {}
}