package com.secondhand.common;

public interface FileLocation {
    String PRODUCT_LOCATION = System.getProperty("user.dir")+"/upload/product/";
    String USER_LOCATION = System.getProperty("user.dir")+"/upload/user/";
    String BRAND_LOCATION = System.getProperty("user.dir")+"/upload/brand/";
    String PRODUCT = "/product/";
    String USER = "/user/";
    String BRAND = "/brand/";

}
