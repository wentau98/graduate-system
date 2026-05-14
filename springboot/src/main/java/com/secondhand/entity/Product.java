package com.secondhand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
//@AllArgsConstructor
@TableName("Product")
public class Product {
    @TableId(type = IdType.AUTO) // 👈 关键！数据库自增
    private Long productId;
    private String productName;
    private Long sellerId;
    private Integer categoryId;
    private Integer brandId;
    private String itemCondition;
    private String functionalDamage;
    private String appearanceDamage;
    private BigDecimal officialPrice;
    private BigDecimal sellingPrice;
    private String productDesc;
    private Integer productStatus;
    private String rejectReason;
    private Integer viewCount;
    private Integer collectCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

//    // ====================== 只加这一个字段（用于后台审核商品） ======================
//    private Integer auditStatus;
//
    // ===================== 关键！新增图片URL字段 =====================
    private String imageUrl;


}