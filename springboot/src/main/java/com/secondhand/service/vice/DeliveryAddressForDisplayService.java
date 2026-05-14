package com.secondhand.service.vice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.secondhand.entity.vice.DeliveryAddressForDisplay;

import java.util.List;

public interface DeliveryAddressForDisplayService extends IService<DeliveryAddressForDisplay> {
    List<DeliveryAddressForDisplay> getAll();
}