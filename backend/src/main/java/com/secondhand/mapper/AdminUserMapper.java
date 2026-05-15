package com.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secondhand.entity.AdminUser;
import org.apache.ibatis.annotations.Select;

public interface AdminUserMapper extends BaseMapper<AdminUser> {
    @Select("SELECT * FROM admin_user WHERE username=#{username}")
    AdminUser selectByUsername(String username);
}