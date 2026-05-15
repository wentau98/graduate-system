package com.secondhand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secondhand.entity.Brand;
import com.secondhand.entity.CategoryBrandRelation;
import com.secondhand.mapper.BrandMapper;
import com.secondhand.mapper.CategoryBrandRelationMapper;
import com.secondhand.service.CategoryBrandRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelation>
        implements CategoryBrandRelationService {

    private final BrandMapper brandMapper;

    @Override
    public void bind(CategoryBrandRelation relation) {
        save(relation);
    }

    @Override
    public List<Brand> getBrandsByCategoryId(Integer categoryId) {
        LambdaQueryWrapper<CategoryBrandRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryBrandRelation::getCategoryId, categoryId);

        List<Integer> brandIds = list(wrapper).stream()
                .map(CategoryBrandRelation::getBrandId)
                .collect(Collectors.toList());

        if (brandIds.isEmpty()) return List.of();
        return brandMapper.selectBatchIds(brandIds);
    }

    @Override
    public Integer getRelationIdByCategoryIdAndBrandId(Integer categoryId, Integer brandId) {
        // 1. 构造查询条件：brandId = ? AND id = ?
        QueryWrapper<CategoryBrandRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", categoryId)    // 字段1 = 参数1
                .eq("brand_id", brandId);              // 字段2 = 参数2

// 2. 查询一条
        CategoryBrandRelation categoryBrandRelation = baseMapper.selectOne(wrapper);
        return categoryBrandRelation.getId();
    }
}