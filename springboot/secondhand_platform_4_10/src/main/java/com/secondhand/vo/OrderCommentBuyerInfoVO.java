package com.secondhand.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderCommentBuyerInfoVO {
    // 评价信息
    private Long commentId;
    private Long orderId;
    private Long productId;
    private Long userId;          // 评价人ID
    private Long sellerId;        // 卖家ID
    private Integer score;        // 评分 1-5
    private String content;       // 评价内容
    private LocalDateTime createTime;

    // 评价人（买家）信息（来自 user_info 表）
    private String username;      // 买家用户名
    private String realName;      // 真实姓名
    private String avatar;        // 买家头像
    private String phone;         // 电话
    private String location;      // 所在地
}
