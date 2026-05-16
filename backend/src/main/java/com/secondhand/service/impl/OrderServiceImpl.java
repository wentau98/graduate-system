package com.secondhand.service.impl;

//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.baomidou.mybatisplus.
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secondhand.UserWebSocketServer;
import com.secondhand.common.CommonResult;
import com.secondhand.common.OrderStatus;
import com.secondhand.entity.OrderMain;
import com.secondhand.mapper.OrderMainMapper;
import com.secondhand.mapper.ProductMapper;
import com.secondhand.service.OrderService;
import com.secondhand.utils.OrderUtil;
import com.secondhand.vo.OrderMainVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMainMapper,OrderMain> implements OrderService {
    @Resource
    OrderMainMapper orderMainMapper;
    @Autowired
    private ProductMapper productMapper;

//    @Override
//    public CommonResult<?> createOrder(OrderMain order) {
//        order.setOrderStatus(OrderStatus.WAIT_PAY);
//        order.setOrderNo(OrderUtil.generateOrderNo());
//        orderMainMapper.insert(order);
//        return CommonResult.success(null);
//    }

//    @Override
    public List<OrderMain> getByUserId(Long uid) {
        return orderMainMapper.selectMyOrder(uid);
    }

//    @Override
    public CommonResult<?> payOrder(Long id) {
        orderMainMapper.updateStatus(id, OrderStatus.WAIT_DELIVER);
        OrderMain orderMain = orderMainMapper.selectById(id);
        productMapper.updateProductStatus(orderMain.getProductId());
        // 3. 组装提醒文案
        String msg = String.format(
                "【二手订单提醒】你的商品被下单！\n订单号：%s\n请尽快处理发货",
                orderMain.getOrderNo()
        );
        UserWebSocketServer.sendToSeller(orderMain.getSellerId().toString(),msg);
        return CommonResult.success(null);
    }



//    @Override
    public CommonResult<?> createOrder(long productId, long buyerId, long sellerId) {
        OrderMain orderMain = new OrderMain();
        orderMain.setUserId(buyerId);
        orderMain.setOrderStatus(OrderStatus.WAIT_PAY);
        orderMain.setOrderNo(OrderUtil.generateOrderNo());
        orderMain.setSellerId(sellerId);
        orderMain.setProductId(productId);
        orderMain.setProductName(productMapper.selectById(productId).getProductName());
        orderMain.setProductPrice(productMapper.selectById(productId).getSellingPrice());

        orderMainMapper.insert(orderMain);
        long orderId = orderMain.getOrderId();
        return CommonResult.success(orderId);
    }

//    @Override
    public void updatePayStatus(String orderId, int i) {


    }
    @Override
    public List<OrderMainVO> getMySellList(Long userId) {
        return orderMainMapper.selectBySellerId(userId);
    }

    @Override
    public CommonResult<List<OrderMainVO>> listOrdersByUserIdAndStaus(Long userId, Integer status) {
        //status:0 传过来的userId作为订单表的卖家sellerId,status:1 传过来的userId作为订单表的买家userId
        try {
            if(status == 0){
                return CommonResult.success(orderMainMapper.selectBySellerId(userId));
            }else{
                return CommonResult.success(orderMainMapper.selectByUserId(userId));
            }
        }catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    @Override
    public List<OrderMainVO> getMyBuyList(Long userId) {
        return orderMainMapper.selectByUserId(userId);
    }

}