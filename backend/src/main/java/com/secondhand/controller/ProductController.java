package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.common.ProductStatus;
import com.secondhand.entity.Product;
import com.secondhand.service.ProductService;
import com.secondhand.vo.ProductDetailVO;
import com.secondhand.vo.ProductPublishVO;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Resource
    ProductService productService;

    @PostMapping("/publish")
    public CommonResult<?> publish(@RequestBody ProductPublishVO productPublishVO) {
        System.out.println("productPublishVO.getImageList" + productPublishVO.getImageList());
        return productService.publish(productPublishVO);
    }

    @GetMapping("/my/{userId}")
    public CommonResult<List<Product>> getMyProduct(@PathVariable Long userId) {
        return CommonResult.success(productService.getMyProductList(userId));
    }
    //same as onSale

    @GetMapping("/detail/{id}")
    public CommonResult<ProductDetailVO> detail(@PathVariable Long id) {
        return CommonResult.success(productService.getById(id));
    }

    @GetMapping("/onSale")
    public CommonResult<List<Product>> getOnSaleList() {
        return CommonResult.success(productService.getOnSaleList());
    }

    @GetMapping("/keyword-search/{keyword}")
    public CommonResult<List<Product>> keywordSearch(@PathVariable("keyword") String keyword) {
        return CommonResult.success(productService.keywordSearch(keyword));
    }

    @GetMapping("/category-search/{categoryId}")
    public CommonResult<List<Product>> categorySearch(@PathVariable("categoryId") long categoryId) {
        return CommonResult.success(productService.categorySearch(categoryId));
    }

    @PutMapping("/viewcount/add/{id}")
    public void updateViewCount(@PathVariable Long id) {
        productService.updateViewCount(id);
    }

    @PutMapping("/status/off-shelf/{id}")
    public void offShelf(@PathVariable Long id) {
        productService.offShelf(id, ProductStatus.OFF_SHELF);
    }
    @PutMapping("/status/up-shelf/{id}")
    public void upShelf(@PathVariable Long id) {
        productService.upShelf(id, ProductStatus.UNDER_REVIEW);
    }
//    @PutMapping("/status/off-shelf/{id}")
//    public void offShelf(@PathVariable Long id) {
//        productService.offShelf(id, ProductStatus.OFF_SHELF);
//    }

//  request.get(`api/product/remind/audit/${productId}`)


}