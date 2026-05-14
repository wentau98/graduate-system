package com.secondhand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.secondhand.entity.Brand;
import com.secondhand.entity.CategoryBrandRelation;
import java.util.List;

public interface CategoryBrandRelationService extends IService<CategoryBrandRelation> {
    void bind(CategoryBrandRelation relation);
    List<Brand> getBrandsByCategoryId(Integer categoryId);

    Integer getRelationIdByCategoryIdAndBrandId(Integer categoryId, Integer brandId);
}