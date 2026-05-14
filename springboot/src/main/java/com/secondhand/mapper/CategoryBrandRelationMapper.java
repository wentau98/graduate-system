package com.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secondhand.entity.CategoryBrandRelation;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface CategoryBrandRelationMapper extends BaseMapper<CategoryBrandRelation> {
    @Select("SELECT b.* FROM brand b LEFT JOIN category_brand_relation r ON b.brand_id=r.brand_id WHERE r.category_id=#{cid}")
    List<CategoryBrandRelation> selectByCategoryId(Integer cid);
}