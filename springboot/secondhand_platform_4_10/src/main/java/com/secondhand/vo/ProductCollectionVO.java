package com.secondhand.vo;

import lombok.Data;

import java.util.Date;
@Data
public class ProductCollectionVO {
    private long productId;
    private String productName;
    private String productDesc;
    private String productImg;
    private Double sellingPrice;
    private int productStatus;
    private int viewCount;
    private int collectCount;
    private Date createTime;
}
