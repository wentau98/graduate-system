package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.common.OrderStatus;
import com.secondhand.common.ProductStatus;
import com.secondhand.entity.OrderMain;
import com.secondhand.entity.Product;
import com.secondhand.service.OrderService;
import com.secondhand.service.ProductService;
import com.secondhand.vo.OrderCreateRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    OrderService orderService;
    @Resource
    ProductService productService;
    @PostMapping("/create")
    public CommonResult<?> create(@RequestBody OrderCreateRequest orderCreateRequest) {
        return orderService.createOrder(
                orderCreateRequest.getProductId(),
                orderCreateRequest.getBuyerId(),
                orderCreateRequest.getSellerId());
    }

    @GetMapping("/list/{userId}")
    public CommonResult<List<OrderMain>> list(@PathVariable Long userId) {
        return CommonResult.success(orderService.getByUserId(userId));
    }
    //手机扫码支付接口
    @GetMapping("/pay/{id}")
    public CommonResult<?> pay(@PathVariable Long id, Model model) {
        model.addAttribute("orderId", id);
        return orderService.payOrder(id);
    }

    //前端检查是否支付接口
    @GetMapping("/status/{orderId}")
    public CommonResult<Integer> getOrderStatus(@PathVariable Long orderId) {
        OrderMain order = orderService.getById(orderId);
        return CommonResult.success(order.getOrderStatus());
    }
    // 我买到的（买家视角：user_id = 登录用户）
    @GetMapping("/buy/list")
    public CommonResult buyList(Long userId) {
        return CommonResult.success(orderService.getMyBuyList(userId));
    }

    // 我卖出的（卖家视角：seller_id = 登录用户）
    @GetMapping("/sell/list")
    public CommonResult sellList(Long userId) {
        return CommonResult.success(orderService.getMySellList(userId));
    }
    //发货成功,改订单状态
    @PutMapping("/sell/delivered/{orderId}")
    public CommonResult delivered(@PathVariable("orderId") Long orderId) {
        return CommonResult.success(orderService.updateById(new OrderMain().setOrderId(orderId).setOrderStatus(OrderStatus.WAIT_RECEIVE)));
    }
    @PutMapping("/receive/{orderId}")
    public CommonResult receivedGoods(@PathVariable("orderId") Long orderId) {
        return CommonResult.success(orderService.updateById(new OrderMain().setOrderId(orderId).setOrderStatus(OrderStatus.COMPLETED)));
    }
    //下架product并且产生的订单置为5：已取消/api/order/off-shelf/${orderId}
    @PutMapping("/off-shelf/{orderId}")
    public CommonResult cancelProductAndOrders(@PathVariable("orderId") Long orderId) {
        orderService.updateById(new OrderMain().setOrderId(orderId).setOrderStatus(OrderStatus.CANCELLED));
        long productId = orderService.getById(orderId).getProductId();
        productService.updateById(new Product().setProductId(productId).setProductStatus(ProductStatus.OFF_SHELF));
        return CommonResult.success(null);
    }
}