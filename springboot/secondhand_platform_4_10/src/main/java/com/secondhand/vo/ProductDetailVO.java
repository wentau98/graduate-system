package com.secondhand.vo;

import com.secondhand.entity.ProductImage;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class ProductDetailVO {
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
    private int productStatus;
    private Integer viewCount;
    private Integer collectCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<ProductImage> imageUrlList;
}
