package com.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secondhand.entity.OrderMain;
import com.secondhand.vo.OrderMainVO;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface OrderMainMapper extends BaseMapper<OrderMain> {
    @Insert("INSERT INTO order_main(order_no,user_id,seller_id,product_id,product_name,product_price,order_status) " +
            "VALUES(#{orderNo},#{userId},#{sellerId},#{productId},#{productName},#{productPrice},#{orderStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId", keyColumn = "order_id")
    int insert(OrderMain order);

    @Select("SELECT * FROM order_main o join product_image pi on o.product_id = pi.product_id WHERE user_id=#{uid} and pi.image_sort =1 order by o.create_time desc")
    List<OrderMainVO> selectByUserId(Long uid);

    @Select("SELECT * FROM order_main o join product_image pi on o.product_id = pi.product_id WHERE seller_id=#{uid} and pi.image_sort =1 order by o.create_time asc")
    List<OrderMainVO> selectBySellerId(Long sid);

    @Select("SELECT * FROM order_main WHERE order_id=#{id}")
    OrderMain selectById(Long id);

    @Update("UPDATE order_main SET order_status=#{status} WHERE order_id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    @Select("SELECT * FROM order_main")
    List<OrderMain> selectAll();

    @Select("SELECT * FROM order_main WHERE user_id=#{uid} order by create_time desc")
    List<OrderMain> selectMyOrder(Long uid);
}