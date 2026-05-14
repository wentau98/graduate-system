package com.secondhand.controller.vice;

import com.secondhand.entity.vice.DeliveryAddressForDisplay;
import com.secondhand.service.vice.DeliveryAddressForDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/school-addresses")
public class SchoolAddressController {

    @Autowired
    private DeliveryAddressForDisplayService service;

    /**
     * 查询全部（下拉框用）
     */
    @GetMapping
    public List<DeliveryAddressForDisplay> getAll() {
        return service.getAll();
    }

    /**
     * 新增一个地址
     */
    @PostMapping
    public boolean add(@RequestBody DeliveryAddressForDisplay address) {
        return service.save(address);
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public boolean update(@PathVariable Long id, @RequestBody DeliveryAddressForDisplay address) {
        address.setId(id);
        return service.updateById(address);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return service.removeById(id);
    }
}