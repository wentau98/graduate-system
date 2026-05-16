package com.secondhand.controller.admin;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.Product;
import com.secondhand.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    }
