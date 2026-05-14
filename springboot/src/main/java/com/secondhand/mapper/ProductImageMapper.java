package com.secondhand.mapper;

import com.secondhand.entity.ProductImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface ProductImageMapper {
    @Insert("INSERT INTO product_image(product_id,image_url,image_sort) VALUES(#{productId},#{imageUrl},#{imageSort})")
    int insert(ProductImage img);

    @Select("SELECT * FROM product_image WHERE product_id=#{pid}")
    List<ProductImage> selectByProductId(Long pid);
}