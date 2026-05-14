package com.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secondhand.entity.ProductCollection;
import com.secondhand.vo.ProductCollectionVO;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface ProductCollectionMapper extends BaseMapper<ProductCollection> {
    @Insert("INSERT INTO product_collection(product_id,user_id) VALUES(#{productId},#{userId})")
    int insert(ProductCollection pc);

    @Delete("DELETE FROM product_collection WHERE product_id=#{pid} AND user_id=#{uid}")
    int delete(@Param("pid") Long pid, @Param("uid") Long uid);

//    @Select("SELECT * FROM product_collection WHERE user_id=#{uid}")
//    List<ProductCollectionVO> selectByUserId(Long uid);
    @Select("SELECT p.product_id,p.product_name AS productName, " +
            "p.product_desc AS productDesc, " +
            "(SELECT image_url FROM product_image WHERE product_id = p.product_id LIMIT 1) AS productImg," +
            " p.selling_price AS sellingPrice, " +
            "p.product_status AS productStatus, " +
            "p.view_count AS viewCount, " +
            "p.collect_count AS collectCount, " +
            "pc.collect_time AS createTime " +
            "FROM product_collection pc " +
            "JOIN product p ON pc.product_id = p.product_id " +
            "WHERE pc.user_id = #{uid} " +
            "ORDER BY pc.collect_time DESC")
    List<ProductCollectionVO> selectByUserId(Long uid);

    /**
     * 根据 用户ID + 商品ID 查询收藏数量
     */
    @Select("SELECT COUNT(*) FROM product_collection " +
            "WHERE user_id = #{userId} AND product_id = #{productId}")
    Integer selectCountByUserAndProduct(@Param("userId") Long userId,@Param("productId") Long productId);
    @Update("update product set collect_count = collect_count+1 where product_id = #{productId}")
    void addProductCollection(Long productId);
    @Update("update product set collect_count = collect_count-1 where product_id = #{productId}")
    void reduceProductCollection(Long productId);
}