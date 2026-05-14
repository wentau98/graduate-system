package com.secondhand.vo;// OrderCreateRequest.java
import lombok.Data;

@Data // 必须加这个注解
public class OrderCreateRequest {
    private Long productId;
    private Long buyerId;
    private Long sellerId;
}