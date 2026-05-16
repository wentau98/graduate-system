package com.secondhand.service.impl;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.UserInfo;
import com.secondhand.entity.Product;
import com.secondhand.entity.OrderMain;
import com.secondhand.mapper.UserInfoMapper;
import com.secondhand.mapper.ProductMapper;
import com.secondhand.mapper.OrderMainMapper;
import com.secondhand.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private OrderMainMapper orderMainMapper;

    // 管理员登录
    @Override
    public CommonResult<?> login(String username, String password) {
        if ("admin".equals(username) && "123456".equals(password)) {
            return CommonResult.success("登录成功");
        }
        return CommonResult.error("账号或密码错误");
    }

    // 查询所有用户
    @Override
    public CommonResult<?> userList() {
        List<UserInfo> list = userInfoMapper.selectList();
        return CommonResult.success(list);
    }

    // 删除用户
    @Override
    public CommonResult<?> deleteUser(Long userId) {
        userInfoMapper.deleteById(userId);
        return CommonResult.success("删除成功");
    }

    // 查询所有商品
    @Override
    public CommonResult<?> productList() {
        List<Product> list = productMapper.selectList();
        return CommonResult.success(list);
    }

    // 修改商品审核状态
    @Override
    public CommonResult<?> allowProductOnSale(Long productId) {
        productMapper.onSale(productId);
        return CommonResult.success("审核状态更新成功");
    }

    // 查询所有订单
    @Override
    public CommonResult<?> orderList() {
        List<OrderMain> list = orderMainMapper.selectAll();
        return CommonResult.success(list);
    }

    @Override
    public CommonResult<?> getUserDetail(Long id) {
        return CommonResult.success(userInfoMapper.selectById(id));
    }
}