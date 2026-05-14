package com.secondhand.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secondhand.entity.ProductCategory;
import com.secondhand.mapper.ProductCategoryMapper;
import com.secondhand.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {
    @Autowired
    private ProductCategoryMapper categoryMapper;

    @Override
    public List<ProductCategory> buildCategoryTree() {
        // 1. 查询所有分类
        List<ProductCategory> allCategories = categoryMapper.selectList(null);

        // 2. 找出一级分类 parentId = 0
        List<ProductCategory> rootList = allCategories.stream()
                .filter(c -> c.getParentId() == 0)
                .collect(Collectors.toList());

        // 3. 递归设置子节点
        for (ProductCategory root : rootList) {
            setChildren(root, allCategories);
        }

        return rootList;
    }

    /**
     * 递归设置子分类
     */
    private void setChildren(ProductCategory parent, List<ProductCategory> allList) {
        List<ProductCategory> children = allList.stream()
                .filter(c -> c.getParentId().equals(parent.getCategoryId()))
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(children)) {
            parent.setChildren(children);
            // 递归设置子节点的子节点
            for (ProductCategory child : children) {
                setChildren(child, allList);
            }
        }
    }
}