package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.Order;
import com.secondhand.entity.OrderMain;
import com.secondhand.service.OrderService;
import com.secondhand.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PayController {
    @Resource
    private OrderService orderService;
    @Resource
    private ProductService productService;
    // 🔥 扫码就访问这里！
//    @GetMapping("/pay/{orderId}")
//    public CommonResult pay(@PathVariable String orderId) {
//        // 1. 标记订单已支付
//        orderService.updatePayStatus(orderId, 1);
//        productService.soldOut(orderId);
//        // 2. 返回支付成功
//        return CommonResult.success("支付成功");
//    }
//
//    // 前端轮询查询状态
//    @GetMapping("/pay/status/{orderId}")
//    public CommonResult getStatus(@PathVariable String orderId) {
//        OrderMain order = orderService.getById(orderId);
//        return CommonResult.success(order.getOrderStatus() == 4);
//    }
}