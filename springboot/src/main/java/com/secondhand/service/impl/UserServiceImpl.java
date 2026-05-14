package com.secondhand.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.UserInfo;
import com.secondhand.mapper.UserInfoMapper;
import com.secondhand.service.UserService;
import com.secondhand.vo.UpdateUserInfoVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    public CommonResult<?> register(UserInfo userInfo) {
        UserInfo exist = userInfoMapper.selectByUsername(userInfo.getUsername());
        if (exist != null) return CommonResult.error("Username already exists");
        userInfoMapper.insert(userInfo);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<?> login(String username, String password) {
        UserInfo user = userInfoMapper.selectByUsername(username);
        if (user == null || !user.getPassword().equals(password))
            return CommonResult.error("Invalid username or password");
        System.out.println(user.getLocation());
        return CommonResult.success(user);
    }

    @Override
    public CommonResult<?> update(UserInfo userInfo) {
        userInfoMapper.updateById(userInfo);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<?> getUserInfo(Long userId) {
        return CommonResult.success(userInfoMapper.selectById(userId));
    }



    @Override
    public CommonResult<?> updateWithVO(UpdateUserInfoVO userInfoVO) {
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInfo::getUserId,userInfoVO.getUserId());
        updateWrapper.set(StrUtil.isNotBlank(userInfoVO.getAvatar()),UserInfo::getAvatar,userInfoVO.getAvatar());
        System.out.println("userInfoVO :"+userInfoVO);
        updateWrapper.set(StrUtil.isNotBlank(userInfoVO.getUsername()), UserInfo::getUsername, userInfoVO.getUsername());
        updateWrapper.set(StrUtil.isNotBlank(userInfoVO.getPassword()), UserInfo::getPassword, userInfoVO.getPassword());
        updateWrapper.set(StrUtil.isNotBlank(userInfoVO.getLocation()), UserInfo::getLocation, userInfoVO.getLocation());
        updateWrapper.set(StrUtil.isNotBlank(userInfoVO.getPhone()), UserInfo::getPhone, userInfoVO.getPhone());
        userInfoMapper.update(null,updateWrapper);
        return CommonResult.success(null);
    }
}