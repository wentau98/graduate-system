package com.secondhand.controller.admin;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.Brand;
import com.secondhand.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/brand")
@RequiredArgsConstructor
public class AdminBrandController {

    private final BrandService brandService;

    // 查询所有品牌
    @GetMapping("/list")
    public CommonResult<List<Brand>> list() {
        return CommonResult.success(brandService.list());
    }

    // 添加品牌
    @PostMapping("/add")
    public CommonResult<?> add(@RequestBody Brand brand) {
        brandService.save(brand);
        return CommonResult.success(null);
    }

    // 修改品牌
    @PostMapping("/update")
    public CommonResult<?> update(@RequestBody Brand brand) {
        brandService.updateById(brand);
        return CommonResult.success(null);
    }

    // 删除品牌：增加异常捕获，打印真实错误
    @GetMapping("/delete/{id}")
    public CommonResult<?> delete(@PathVariable Integer id) {
        try {
            // 先删除分类-品牌关联数据，再删除品牌
            // 若没有关联表，可直接执行 removeById
            // categoryBrandService.remove(Wrappers.lambdaQuery(CategoryBrand::getBrandId, id));
            brandService.removeById(id);
            return CommonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace(); // IDEA控制台会打印真实错误
            return CommonResult.failed("删除失败：" + e.getMessage());
        }
    }
}