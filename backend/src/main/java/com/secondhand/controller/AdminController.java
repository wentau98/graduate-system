package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "管理员管理接口", description = "管理员登录、用户管理、商品管理、订单管理")
public class AdminController {

    @Resource
    private AdminService adminService;

    // ====================== 登录 ======================
    @PostMapping("/login")
    @Operation(summary = "管理员登录")
    public CommonResult<?> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        return adminService.login(username, password);
    }

    // ====================== 用户管理 ======================
    @GetMapping("/user/list")
    @Operation(summary = "查询所有用户")
    public CommonResult<?> userList() {
        return adminService.userList();
    }

    @DeleteMapping("/user/delete/{userId}")
    @Operation(summary = "删除用户")
    public CommonResult<?> deleteUser(
            @PathVariable Long userId
    ) {
        return adminService.deleteUser(userId);
    }
//    @Operation(summary = "查询用户详情信息")
    @GetMapping("/user/detail/{id}")
    public CommonResult<?> userDetail(@PathVariable Long id) {
        return adminService.getUserDetail(id);
    }
    // ====================== 商品管理 ======================
    @GetMapping("/product/list")
    @Operation(summary = "查询所有商品")
    public CommonResult<?> productList() {
        return adminService.productList();
    }

    @PostMapping("/product/put-on-self")
    @Operation(summary = "修改商品状态（审核通过/拒绝）")
    public CommonResult<?> allowProductOnSale(@RequestParam Long productId) {
        return adminService.allowProductOnSale(productId);
    }

    // ====================== 订单管理 ======================
    @GetMapping("/order/list")
    @Operation(summary = "查询所有订单")
    public CommonResult<?> orderList() {
        return adminService.orderList();
    }


}