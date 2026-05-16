package com.secondhand.controller.admin;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.Product;
import com.secondhand.service.OrderService;
import com.secondhand.service.ProductService;
import com.secondhand.vo.OrderMainVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/admin")
@Tag(name = "管理员管理订单接口", description = "..")
public class AdminOrderController {
    @Resource
    private OrderService orderService;
    //status:0 传过来的userId作为订单表的卖家sellerId,status:1 传过来的userId作为订单表的买家userId
    @GetMapping("/users/{userId}/orders/{status}")
    public CommonResult<List<OrderMainVO>> getUserGoods(@PathVariable("userId") Long userId,@PathVariable("status") Integer status) {
        return orderService.listOrdersByUserIdAndStaus(userId,status);
    }


}
