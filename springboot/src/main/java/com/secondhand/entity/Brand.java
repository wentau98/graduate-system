package com.secondhand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Brand {
    @TableId(type = IdType.AUTO) // Or IdType.ASSIGN_ID for Snowflake
    private Integer brandId;
    private String brandName;
    private String brandLogo;
    private Integer sort;
    private LocalDateTime createTime;
}