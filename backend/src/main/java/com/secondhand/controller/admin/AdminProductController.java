package com.secondhand.controller.admin;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.Product;
import com.secondhand.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "管理员管理商品接口", description = "..")
public class AdminProductController {
    @Resource
    private ProductService productService;

    @GetMapping("/users/{userId}/goods")
    public CommonResult<List<Product>> getUserGoods(@PathVariable Long userId) {
        return productService.listGoodsByUserId(userId);
    }

    @PutMapping("/audit/goods")
    public CommonResult<?> auditAllGoods() {
        return productService.auditAllGoods();
    }
    @PutMapping("/audit-fail/good/{productId}")
    public CommonResult<?> reject(@PathVariable("productId") Long productId) {
        return productService.auditRejectGoods(productId);
    }

}
