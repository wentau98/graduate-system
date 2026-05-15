package com.secondhand.mapper;

import com.secondhand.entity.TradeRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface TradeRecordMapper {
    @Insert("INSERT INTO trade_record(order_id,user_id,trade_type,amount,trade_status) " +
            "VALUES(#{orderId},#{userId},#{tradeType},#{amount},#{tradeStatus})")
    int insert(TradeRecord tr);

    @Select("SELECT * FROM trade_record WHERE order_id=#{oid}")
    List<TradeRecord> selectByOrderId(Long oid);
}