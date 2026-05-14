package com.secondhand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.Product;
import com.secondhand.entity.ProductImage;
import com.secondhand.mapper.ProductImageMapper;
import com.secondhand.mapper.ProductMapper;
import com.secondhand.service.ProductService;
import com.secondhand.vo.ProductDetailVO;
import com.secondhand.vo.ProductPublishVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    ProductMapper productMapper;
    @Resource
    ProductImageMapper productImageMapper;

    @Override
    public CommonResult<?> publish(Product product) {
        System.out.println("product"+product);
        productMapper.insert(product);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<?> publish(ProductPublishVO productPublishVO) {
        Product product = new Product(); // 无参构造
        product.setProductName(productPublishVO.getProductName());
        product.setCategoryId(productPublishVO.getCategoryId());
        product.setBrandId(productPublishVO.getBrandId());
        product.setItemCondition(productPublishVO.getItemCondition());
        product.setFunctionalDamage(productPublishVO.getFunctionalDamage());
        product.setAppearanceDamage(productPublishVO.getAppearanceDamage());
        product.setOfficialPrice(productPublishVO.getOfficialPrice());
        product.setSellingPrice(productPublishVO.getSellingPrice());
        product.setProductDesc(productPublishVO.getProductDesc());
        product.setSellerId(productPublishVO.getSellerId());
        product.setProductStatus(4); // 审核中
        product.setRejectReason(null);
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());

        // 插入商品
        productMapper.insert(product);
        Long productId = product.getProductId();
        System.out.println("productId"+productId);
        // 插入图片
        List<String> imageList = productPublishVO.getImageList();
        System.out.println("imageList"+imageList);
        if (imageList != null && !imageList.isEmpty()) {
            for (int i = 0; i < imageList.size(); i++) {
                System.out.println("imageList"+imageList.get(i));
                System.out.println("image ");
                ProductImage image = new ProductImage();
                image.setProductId(productId);
                image.setImageUrl(imageList.get(i));
                image.setImageSort(i+1);
                image.setCreateTime(LocalDateTime.now());
                productImageMapper.insert(image);
            }
        }

        return CommonResult.success("发布成功");
    }

    @Override
    public List<Product> getOnSaleList() {
        return productMapper.selectOnSale();
    }

    @Override
    public ProductDetailVO getById(Long id) {
        ProductDetailVO productDetailVO = new ProductDetailVO();
        Product product = productMapper.selectById(id);
        productDetailVO.setProductId(product.getProductId());
        productDetailVO.setProductName(product.getProductName());
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setBrandId(product.getBrandId());
        productDetailVO.setItemCondition(product.getItemCondition());
        productDetailVO.setFunctionalDamage(product.getFunctionalDamage());
        productDetailVO.setAppearanceDamage(product.getAppearanceDamage());
        productDetailVO.setOfficialPrice(product.getOfficialPrice());
        productDetailVO.setSellingPrice(product.getSellingPrice());
        productDetailVO.setProductStatus(product.getProductStatus());
        productDetailVO.setProductDesc(product.getProductDesc());
        productDetailVO.setSellerId(product.getSellerId());
        productDetailVO.setImageUrlList(productImageMapper.selectByProductId(id));
        return productDetailVO;
    }

    @Override
    public List<Product> keywordSearch(String keyword) {
        return productMapper.getListByKeyword(keyword);
    }

    @Override
    public List<Product> categorySearch(long categoryId) {
        return productMapper.getListByCategory(categoryId);
    }

    @Override
    public List<Product> getMyProductList(Long userId) {
        return  productMapper.getListByUserId(userId);
    }

    @Override
    public void updateViewCount(Long id) {
        productMapper.updateViewCount(id);
    }

    @Override
    public void offShelf(Long id, int offShelf) {
        Product product = new Product();
        product.setProductId(id);
        product.setProductStatus(offShelf);
        productMapper.updateById(product);
    }
}