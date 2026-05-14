package com.secondhand.mapper.vice;

import com.secondhand.vo.DashboardVO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import java.util.List;
import java.util.Map;

public interface DashboardMapper {

    // 1. 总统计数字
    @Select("SELECT " +
            "(SELECT COUNT(*) FROM user_info) AS userTotal, " +
            "(SELECT COUNT(*) FROM product WHERE product_status=1) AS productPublished, " +
            "(SELECT COUNT(*) FROM order_main WHERE order_status=4) AS orderFinished, " +
            "(SELECT COUNT(*) FROM user_info WHERE user_status=2) AS userViolation, " +
            "(SELECT COUNT(*) FROM product WHERE product_status=5) AS productViolation, " +
            "(SELECT COUNT(DISTINCT order_id) FROM refund_record) AS orderDispute")
    Map<String, Object> getTotalNumbers();

    // 2. 日新增用户折线
    @Select("SELECT DATE(create_time) AS date, COUNT(*) AS count FROM user_info GROUP BY DATE(create_time) ORDER BY date")
    List<Map<String, Object>> getUserTrend();

    // 3. 日新增商品折线
    @Select("SELECT DATE(create_time) AS date, COUNT(*) AS count FROM product GROUP BY DATE(create_time) ORDER BY date")
    List<Map<String, Object>> getProductTrend();

    // 4. 日完成订单折线
    @Select("SELECT DATE(create_time) AS date, COUNT(*) AS count FROM order_main WHERE order_status=4 GROUP BY DATE(create_time) ORDER BY date")
    List<Map<String, Object>> getOrderTrend();

    // 5. 商品状态饼图
    @Select("SELECT " +
            "CASE product_status WHEN 1 THEN '上架' WHEN 2 THEN '下架' WHEN 5 THEN '违规' ELSE '其他' END AS name, " +
            "COUNT(*) AS value FROM product GROUP BY product_status")
    List<Map<String, Object>> getProductStatusPie();

    // 6. 订单状态饼图
    @Select("SELECT " +
            "CASE order_status WHEN 1 THEN '待付款' WHEN 2 THEN '待发货' WHEN 3 THEN '待收货' WHEN 4 THEN '已完成' WHEN 5 THEN '已取消' ELSE '其他' END AS name, " +
            "COUNT(*) AS value FROM order_main GROUP BY order_status")
    List<Map<String, Object>> getOrderStatusPie();
}