package com.secondhand.mapper;

import com.secondhand.entity.UserAddress;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface UserAddressMapper {
    @Select("SELECT * FROM user_address WHERE user_id=#{userId}")
    List<UserAddress> selectByUserId(Long userId);

    @Insert("INSERT INTO user_address(user_id,receiver_name,receiver_phone,province,city,district,detail_address,is_default) " +
            "VALUES(#{userId},#{receiverName},#{receiverPhone},#{province},#{city},#{district},#{detailAddress},#{isDefault})")
    int insert(UserAddress addr);

    @Update("UPDATE user_address SET receiver_name=#{receiverName},receiver_phone=#{receiverPhone}," +
            "province=#{province},city=#{city},district=#{district},detail_address=#{detailAddress},is_default=#{isDefault} " +
            "WHERE address_id=#{addressId}")
    int updateById(UserAddress addr);

    @Delete("DELETE FROM user_address WHERE address_id=#{id}")
    int deleteById(Long id);
}