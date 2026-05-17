package com.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.UserInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("SELECT * FROM user_info WHERE user_id = #{userId}")
    UserInfo selectById(Long userId);

    @Select("SELECT * FROM user_info WHERE username = #{username}")
    UserInfo selectByUsername(String username);

    @Insert("INSERT INTO user_info(username,password,real_name,student_id,phone,avatar) " +
            "VALUES(#{username},#{password},#{realName},#{studentId},#{phone},#{avatar})")
    int insert(UserInfo userInfo);

    @Update("UPDATE user_info SET real_name=#{realName},phone=#{phone},avatar=#{avatar} " +
            "WHERE user_id=#{userId}")
    int updateById(UserInfo userInfo);


    // ======================== 只追加这 2 个方法（给后台管理用）========================
    // 1. 查询所有用户（后台用户列表）
    @Select("SELECT * FROM user_info")
    List<UserInfo> selectList();

    // 2. 根据ID删除用户（后台删除用户）
    @Delete("DELETE FROM user_info WHERE user_id = #{userId}")
    int deleteById(Long userId);
@Select("select user_info.user_id from user_info where username = #{admin}")
    int getUserIdByUsername(String admin);

//    @Update("UPDATE user_info SET username=#{username},phone=#{phone},avatar=#{avatar},location=#{location}, " +
//            "WHERE user_id=#{userId}")
//    CommonResult<?> updateAvatarWithVO(int id);
}