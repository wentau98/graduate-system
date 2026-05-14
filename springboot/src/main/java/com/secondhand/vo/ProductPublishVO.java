package com.secondhand.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductPublishVO {
    private String productName;
    private Integer categoryId;
    private Integer brandId;
    private String itemCondition;
    private String functionalDamage;
    private String appearanceDamage;
    private BigDecimal officialPrice;
    private BigDecimal sellingPrice;
    private String productDesc;
    private Long sellerId;
    private List<String> imageList; // 图片URL数组
}