package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.UserAddress;
import com.secondhand.service.UserAddressService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@RestController
@RequestMapping("/api/user/address")
public class UserAddressController {

    @Resource
    private UserAddressService userAddressService;

    @PostMapping("/add")
    public CommonResult<?> add(@RequestBody UserAddress address) {
        return userAddressService.addAddress(address);
    }

    @PutMapping("/update")
    public CommonResult<?> update(@RequestBody UserAddress address) {
        return userAddressService.updateAddress(address);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResult<?> delete(@PathVariable Long id) {
        return userAddressService.deleteAddress(id);
    }

    @GetMapping("/list/{userId}")
    public CommonResult<List<UserAddress>> list(@PathVariable Long userId) {
        return userAddressService.getAddressList(userId);
    }
}