package com.secondhand.entity.vice;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("delivery_address_for_display")
public class DeliveryAddressForDisplay {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String schoolAddress;

//    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}