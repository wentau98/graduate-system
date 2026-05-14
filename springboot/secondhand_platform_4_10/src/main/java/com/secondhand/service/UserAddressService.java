package com.secondhand.service;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.UserAddress;
import java.util.List;

public interface UserAddressService {
    CommonResult<?> addAddress(UserAddress address);
    CommonResult<?> updateAddress(UserAddress address);
    CommonResult<?> deleteAddress(Long addressId);
    CommonResult<List<UserAddress>> getAddressList(Long userId);
}