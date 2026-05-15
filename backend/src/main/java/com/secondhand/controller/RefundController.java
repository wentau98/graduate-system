package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.RefundRecord;
import com.secondhand.service.RefundService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x

@RestController
@RequestMapping("/api/refund")
public class RefundController {

    @Resource
    private RefundService refundService;

    @PostMapping("/apply")
    public CommonResult<?> apply(@RequestBody RefundRecord refund) {
        return refundService.applyRefund(refund);
    }

    @PutMapping("/audit/{id}")
    public CommonResult<?> audit(@PathVariable Long id, @RequestParam Integer status) {
        return refundService.auditRefund(id, status);
    }

    @GetMapping("/order/{orderId}")
    public CommonResult<RefundRecord> getByOrder(@PathVariable Long orderId) {
        return refundService.getRefundByOrderId(orderId);
    }
}