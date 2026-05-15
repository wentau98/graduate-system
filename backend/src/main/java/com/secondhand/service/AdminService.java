package com.secondhand.service;

import com.secondhand.common.CommonResult;

public interface AdminService {
    CommonResult<?> login(String username, String password);
    CommonResult<?> userList();
    CommonResult<?> deleteUser(Long userId);
    CommonResult<?> productList();
    CommonResult<?> allowProductOnSale(Long productId);
    CommonResult<?> orderList();
}