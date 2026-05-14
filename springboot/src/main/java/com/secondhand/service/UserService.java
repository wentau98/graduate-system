package com.secondhand.service;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.UserInfo;
import com.secondhand.vo.UpdateUserInfoVO;

public interface UserService {
    CommonResult<?> register(UserInfo userInfo);
    CommonResult<?> login(String username, String password);
    CommonResult<?> update(UserInfo userInfo);

    CommonResult<?> getUserInfo(Long userId);

    CommonResult<?> updateWithVO(UpdateUserInfoVO userInfoVO);
}