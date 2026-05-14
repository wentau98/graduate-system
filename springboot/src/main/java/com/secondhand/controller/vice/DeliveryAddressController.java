package com.secondhand.controller.vice;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.vice.DeliveryAddress;
import com.secondhand.service.vice.DeliveryAddressService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-address")
public class DeliveryAddressController {

    @Resource
    private DeliveryAddressService deliveryAddressService;

//    // 查询全部
//    @GetMapping
//    public List<DeliveryAddress> getAll() {
//        return deliveryAddressService.getAll();
//    }

    // 新增
    @PostMapping
    public boolean add(@RequestBody DeliveryAddress DeliveryAddress) {
        return deliveryAddressService.save(DeliveryAddress);
    }

    // 修改
    @PutMapping("/{id}")
    public boolean update(@PathVariable Long id, @RequestBody DeliveryAddress DeliveryAddress) {
        DeliveryAddress.setId(id);
        return deliveryAddressService.updateById(DeliveryAddress);
    }

    // 删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return deliveryAddressService.removeById(id);
    }
    // 查询全部
    @GetMapping("/{orderId}")
    public CommonResult<DeliveryAddress> getOne(@PathVariable("orderId") Long orderId) {
        return CommonResult.success(deliveryAddressService.selectByOrderId(orderId));
    }
}