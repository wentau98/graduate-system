package com.secondhand.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.OrderMain;
import com.secondhand.vo.OrderMainVO;

import java.util.List;

public interface OrderService extends IService<OrderMain> {
//    CommonResult<?> createOrder(OrderMain order);
    List<OrderMain> getByUserId(Long uid);
    CommonResult<?> payOrder(Long id);



    CommonResult<?> createOrder(long productId, long buyerId, long sellerId);

    void updatePayStatus(String orderId, int i);

    List<OrderMainVO> getMyBuyList(Long userId);

    List<OrderMainVO> getMySellList(Long userId);

    CommonResult<List<OrderMainVO>> listOrdersByUserIdAndStaus(Long userId, Integer status);
}