package com.secondhand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryBrandRelation {
    @TableId(type = IdType.AUTO) // Or IdType.ASSIGN_ID for Snowflake
    private Integer id;
    private Integer categoryId;
    private Integer brandId;
    private LocalDateTime createTime;
}