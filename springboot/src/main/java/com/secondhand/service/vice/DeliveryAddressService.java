package com.secondhand.service.vice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.secondhand.entity.vice.DeliveryAddress;

import java.util.List;

public interface DeliveryAddressService extends IService<DeliveryAddress> {
    List<DeliveryAddress> getAll();

    DeliveryAddress selectByOrderId(Long orderId);
}