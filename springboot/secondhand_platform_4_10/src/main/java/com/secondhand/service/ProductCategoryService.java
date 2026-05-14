package com.secondhand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.secondhand.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService extends IService<ProductCategory> {
    List<ProductCategory> buildCategoryTree();
}