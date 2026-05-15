package com.secondhand.entity.vice;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("delivery_address")
public class DeliveryAddress {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;       // 订单ID
    private String schoolAddress; // 校内地址
    private String detailAddress; // 详细地址

//    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}