package com.secondhand.mapper;

import com.secondhand.entity.RefundRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RefundRecordMapper {
    @Insert("INSERT INTO refund_record(order_id,user_id,refund_reason,refund_desc,refund_image,refund_amount,refund_status) " +
            "VALUES(#{orderId},#{userId},#{refundReason},#{refundDesc},#{refundImage},#{refundAmount},#{refundStatus})")
    int insert(RefundRecord rr);

    @Select("SELECT * FROM refund_record WHERE order_id=#{oid}")
    RefundRecord selectByOrderId(Long oid);

    @Update("UPDATE refund_record SET refund_status=#{status} WHERE refund_id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}