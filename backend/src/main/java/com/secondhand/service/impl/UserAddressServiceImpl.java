package com.secondhand.service.impl;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.UserAddress;
import com.secondhand.mapper.UserAddressMapper;
import com.secondhand.service.UserAddressService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Resource
    private UserAddressMapper userAddressMapper;

    @Override
    public CommonResult<?> addAddress(UserAddress address) {
        userAddressMapper.insert(address);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<?> updateAddress(UserAddress address) {
        userAddressMapper.updateById(address);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<?> deleteAddress(Long addressId) {
        userAddressMapper.deleteById(addressId);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<List<UserAddress>> getAddressList(Long userId) {
        List<UserAddress> list = userAddressMapper.selectByUserId(userId);
        return CommonResult.success(list);
    }
}