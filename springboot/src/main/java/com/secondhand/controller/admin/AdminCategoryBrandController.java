package com.secondhand.controller.admin;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.Brand;
import com.secondhand.entity.CategoryBrandRelation;
import com.secondhand.service.CategoryBrandRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/category/brand")
@RequiredArgsConstructor
public class AdminCategoryBrandController {

    private final CategoryBrandRelationService relationService;

    // 绑定分类和品牌
    @PostMapping("/bind")
    public CommonResult<?> bind(@RequestBody CategoryBrandRelation relation) {
        relationService.save(relation);
        return CommonResult.success(null);
    }

    // 解绑
    @GetMapping("/unbind/{id}")
    public CommonResult<?> unbind(@PathVariable Integer id) {
        relationService.removeById(id);
        return CommonResult.success(null);
    }

    // 根据分类ID查品牌
    @GetMapping("/list/{categoryId}")
    public CommonResult<List<Brand>> list(@PathVariable Integer categoryId) {
        return CommonResult.success(relationService.getBrandsByCategoryId(categoryId));
    }
    // 根据cid和bid查找rid
    @GetMapping("/unbind1/getrId/{categoryId}/{brandId}")
    public CommonResult<?> getrId(@PathVariable Integer categoryId,
                                  @PathVariable Integer brandId) {
        return CommonResult.success(relationService.getRelationIdByCategoryIdAndBrandId(categoryId,brandId));
    }
}