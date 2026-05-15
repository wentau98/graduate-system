package com.secondhand.service.impl.vice;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secondhand.entity.vice.DeliveryAddressForDisplay;
import com.secondhand.mapper.vice.DeliveryAddressForDisplayMapper;
import com.secondhand.service.vice.DeliveryAddressForDisplayService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeliveryAddressForDisplayServiceImpl
        extends ServiceImpl<DeliveryAddressForDisplayMapper, DeliveryAddressForDisplay>
        implements DeliveryAddressForDisplayService {

    @Override
    public List<DeliveryAddressForDisplay> getAll() {
        return list();
    }
}