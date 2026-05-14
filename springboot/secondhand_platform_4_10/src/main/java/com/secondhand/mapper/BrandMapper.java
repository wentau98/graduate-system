package com.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secondhand.entity.Brand;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface BrandMapper extends BaseMapper<Brand> {
    @Select("SELECT * FROM brand")
    List<Brand> selectAll();
}