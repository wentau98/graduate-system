package com.secondhand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.ProductCollection;
import com.secondhand.vo.ProductCollectionVO;

import java.util.List;

public interface ProductCollectionService extends IService<ProductCollection> {
    CommonResult<?> collect(ProductCollection collection);
    CommonResult<?> cancelCollect(Long productId, Long userId);
    CommonResult<List<ProductCollectionVO>> myCollection(Long userId);
    // 添加：判断是否收藏
    boolean isCollected(Long userId, Long productId);

    CommonResult<Integer> getCollectionNumber(Long productId);
}