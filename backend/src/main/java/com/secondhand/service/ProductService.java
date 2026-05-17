package com.secondhand.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.Product;
import com.secondhand.vo.ProductDetailVO;
import com.secondhand.vo.ProductPublishVO;

import java.util.List;

public interface ProductService extends IService<Product> {
    CommonResult<?> publish(Product product);

    CommonResult<?> publish(ProductPublishVO productPublishVO);
    List<Product> getOnSaleList();
    ProductDetailVO getById(Long id);


    List<Product> keywordSearch(String keyword);

    List<Product> categorySearch(long categoryId);

    List<Product> getMyProductList(Long userId);

    void updateViewCount(Long id);

    void offShelf(Long id, int offShelf);

    CommonResult<List<Product>> listGoodsByUserId(Long userId);

    CommonResult<?> auditAllGoods();

    CommonResult<?> auditRejectGoods(Long productId);

    void upShelf(Long id, int onSale);

//    void soldOut(String orderId);
}