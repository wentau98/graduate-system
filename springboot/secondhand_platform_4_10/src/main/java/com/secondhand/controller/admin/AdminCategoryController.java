package com.secondhand.controller.admin;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.ProductCategory;
import com.secondhand.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final ProductCategoryService categoryService;

    // 查询所有分类
    @GetMapping("/list")
    public CommonResult<List<ProductCategory>> list() {
        return CommonResult.success(categoryService.list());
    }

    // 新增分类
    @PostMapping("/add")
    public CommonResult<?> add(@RequestBody ProductCategory category) {
        System.out.println("=====================");
        System.out.println(category); // 看这里
        System.out.println(category.getCategoryName());
        categoryService.save(category);
        return CommonResult.success(null);
    }

    // 修改分类
    @PostMapping("/update")
    public CommonResult<?> update(@RequestBody ProductCategory category) {
        categoryService.updateById(category);
        return CommonResult.success(null);
    }

    // 删除分类
    @GetMapping("/delete/{id}")
    public CommonResult<?> delete(@PathVariable Integer id) {
        categoryService.removeById(id);
        return CommonResult.success(null);
    }
    /**
     * 获取商品分类树形结构
     */
    @GetMapping("/tree")
    public CommonResult<List<ProductCategory>> getCategoryTree() {
        List<ProductCategory> treeList = categoryService.buildCategoryTree();
        System.out.println(treeList);
        return CommonResult.success(treeList);
    }

}