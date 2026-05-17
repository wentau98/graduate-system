package com.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.Product;
import com.secondhand.vo.ProductDetailVO;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {

//    // ======================== 你原来的全部代码（完整保留！） ========================
//    @Insert("INSERT INTO product(product_name,seller_id,category_id,brand_id,item_condition,functional_damage,appearance_damage,official_price,selling_price,product_desc) " +
//            "VALUES(#{productName},#{sellerId},#{categoryId},#{brandId},#{itemCondition},#{functionalDamage},#{appearanceDamage},#{officialPrice},#{sellingPrice},#{productDesc})")
//    int insert(Product product);

    @Select("SELECT p.*, pi.image_url AS imageUrl " +
            "FROM product p " +
            "LEFT JOIN product_image pi ON p.product_id = pi.product_id " +
            "WHERE p.product_status = 1 and pi.image_sort = 1 " +
            "ORDER BY p.create_time DESC")
    List<Product> selectOnSale();

    @Select("SELECT * FROM product WHERE product_id=#{id}")
    Product selectById(Long id);

    @Select("SELECT * FROM product WHERE seller_id=#{sid}")
    List<Product> selectBySellerId(Long sid);

    @Update("UPDATE product SET product_status= 1 WHERE product_id=#{id} and product_status = 4")
    int onSale(@Param("id") Long id);

    // ======================== 只追加这 3 个方法（给后台管理用） ========================
    // 1. 查询所有商品（后台列表）
    @Select("SELECT * FROM product ORDER BY create_time DESC")
    List<Product> selectList();

    @Select("SELECT p.*, pi.image_url AS imageUrl " +
            "FROM product p " +
            "LEFT JOIN product_image pi ON p.product_id = pi.product_id " +
            "WHERE p.product_status = 1 " +
            "and p.product_desc like concat('%',#{keyword},'%')"+
            "ORDER BY p.create_time DESC")
    List<Product> getListByKeyword(String keyword);

    @Select("SELECT p.*, pi.image_url AS imageUrl " +
            "FROM product p " +
            "LEFT JOIN product_image pi ON p.product_id = pi.product_id " +
            "WHERE p.product_status = 1 " +
            "and p.category_id = #{categoryId} "+
            "ORDER BY p.create_time DESC")
    List<Product> getListByCategory(long categoryId);
    @Update("update product set product_status = 3 where product_id = #{productId}")
    void updateProductStatus(Long productId);
    @Select("SELECT p.*, pi.image_url AS imageUrl " +
            "FROM product p " +
            "LEFT JOIN product_image pi ON p.product_id = pi.product_id " +
            "where p.seller_id = #{userId} and pi.image_sort = 1 "+
            "ORDER BY p.create_time DESC")
    List<Product> getListByUserId(Long userId);
    @Update("update product set view_count = view_count+1 where product_id = #{id}")
    void updateViewCount(Long id);
    @Select("SELECT p.*, pi.image_url AS imageUrl " +
            "FROM product p " +
            "LEFT JOIN product_image pi ON p.product_id = pi.product_id " +
            "where p.seller_id = #{userId} and pi.image_sort = 1 "+
            "ORDER BY p.create_time DESC")
    List<Product> selectListByUserId(Long userId);
    @Update("update product set product_status = 1 where product_status = 4")
    int updateAllProductStatus();
    @Update("update product set product_status = 5 where product_id = #{productId} and product_status IN(1,4)")
    int rejectUpdateGoods(Long productId);
//
//    // 2. 更新商品审核状态（后台审核）
//    @Update("UPDATE product SET product_status = #{auditStatus} WHERE product_id = #{productId}")
//    int updateAuditStatus(@Param("productId") Long productId, @Param("auditStatus") Integer auditStatus);

//    // 3. 根据ID更新商品（通用）
//    @Update("<script>"
//            + "UPDATE product "
//            + "<set>"
//            + "  <if test='productName != null'>product_name=#{productName},</if>"
//            + "  <if test='auditStatus != null'>audit_status=#{auditStatus},</if>"
//            + "  <if test='productStatus != null'>product_status=#{productStatus},</if>"
//            + "  <if test='rejectReason != null'>reject_reason=#{rejectReason},</if>"
//            + "</set>"
//            + "WHERE product_id=#{productId}"
//            + "</script>")
//    int updateById(Product product);
}