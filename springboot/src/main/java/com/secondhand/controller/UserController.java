package com.secondhand.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.UserInfo;
import com.secondhand.service.UserService;
import com.secondhand.vo.UpdateUserInfoVO;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import cn.hutool.core.util.StrUtil;
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/register")
    public CommonResult<?> register(@RequestBody UserInfo userInfo) {
        return userService.register(userInfo);
    }

    @PostMapping("/login")
    public CommonResult<?> login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @PutMapping("/profile")
    public CommonResult<?> update(@RequestBody UserInfo userInfo) {
        return userService.update(userInfo);
    }

    @GetMapping("/get/{userId}")
    public CommonResult<?> getInfo(@PathVariable Long userId) {
        return userService.getUserInfo(userId);
    }
    @PutMapping("/update")
    public CommonResult<?> updateUserInfo(@RequestBody UpdateUserInfoVO userInfoVO) {
        System.out.println("userInfoVO avatar:"+userInfoVO.getAvatar());
        return userService.updateWithVO(userInfoVO);
    }
}