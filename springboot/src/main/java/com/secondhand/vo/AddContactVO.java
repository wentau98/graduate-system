package com.secondhand.vo;

import lombok.Data;

@Data
public class AddContactVO {
    private Long userId;    // 当前用户
    private Long sellerId;  // 对方用户（商家）
}