package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.ProductCollection;
import com.secondhand.service.ProductCollectionService;
import com.secondhand.vo.ProductCollectionVO;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@RestController
@RequestMapping("/api/collection")
public class ProductCollectionController {

    @Resource
    private ProductCollectionService productCollectionService;

    @PostMapping("/add")
    public CommonResult<?> add(@RequestBody ProductCollection collection) {
        return productCollectionService.collect(collection);
    }

    @DeleteMapping("/cancel/{productId}/{userId}")
    public CommonResult<?> cancel(@PathVariable("productId") Long productId, @PathVariable("userId") Long userId) {
        return productCollectionService.cancelCollect(productId, userId);
    }

    @GetMapping("/list/{userId}")
    public CommonResult<List<ProductCollectionVO>> list(@PathVariable Long userId) {
        return productCollectionService.myCollection(userId);
    }

    @GetMapping("/isCollected/{userId}/{productId}")
    public CommonResult<?> isCollected(
            @PathVariable Long userId,
            @PathVariable Long productId
    ) {
        // 调用 service 判断是否收藏
        boolean collected = productCollectionService.isCollected(userId, productId);
        return CommonResult.success(collected);
    }
    @GetMapping("/get/{productId}")
    public CommonResult<Integer> get(@PathVariable Long productId) {
        return productCollectionService.getCollectionNumber(productId);
    }
}