package com.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secondhand.entity.ProductCategory;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    @Select("SELECT * FROM product_category WHERE parent_id=#{parentId}")
    List<ProductCategory> selectByParentId(Integer parentId);

    @Select("SELECT * FROM product_category")
    List<ProductCategory> selectAll();
}