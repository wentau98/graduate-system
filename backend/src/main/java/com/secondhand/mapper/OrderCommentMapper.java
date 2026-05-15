package com.secondhand.mapper;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.OrderComment;
import com.secondhand.vo.OrderCommentBuyerInfoVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface OrderCommentMapper {
    @Insert("INSERT INTO order_comment(order_id,product_id,user_id,seller_id,score,content) " +
            "VALUES(#{orderId},#{productId},#{userId},#{sellerId},#{score},#{content})")
    int insert(OrderComment oc);

    @Select("SELECT * FROM order_comment WHERE product_id=#{pid}")
    List<OrderComment> selectByProductId(Long pid);
    @Select("select * from order_comment oc join user_info u on oc.user_id = u.user_id where oc.seller_id = #{id}")
    CommonResult<OrderCommentBuyerInfoVO> getCommentsBySellerId(Long id);//展示个人中心的评价
}