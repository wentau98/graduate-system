package com.secondhand.service.impl.vice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secondhand.entity.vice.DeliveryAddress;
import com.secondhand.mapper.vice.DeliveryAddressMapper;
import com.secondhand.service.vice.DeliveryAddressService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeliveryAddressServiceImpl
        extends ServiceImpl<DeliveryAddressMapper, DeliveryAddress>
        implements DeliveryAddressService {
    @Resource
    DeliveryAddressMapper deliveryAddressMapper;
    @Override
    public List<DeliveryAddress> getAll() {
        return list();
    }

    @Override
    public DeliveryAddress selectByOrderId(Long orderId) {
        return deliveryAddressMapper.selectOne(new LambdaQueryWrapper<DeliveryAddress>().eq(DeliveryAddress::getOrderId,orderId));
    }
}