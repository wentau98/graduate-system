package com.secondhand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.ProductCollection;
import com.secondhand.mapper.ProductCategoryMapper;
import com.secondhand.mapper.ProductCollectionMapper;
import com.secondhand.service.ProductCollectionService;
import com.secondhand.vo.ProductCollectionVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@Service
public class ProductCollectionServiceImpl extends ServiceImpl<ProductCollectionMapper,ProductCollection> implements ProductCollectionService {

    @Resource
    private ProductCollectionMapper productCollectionMapper;

    @Override
    public CommonResult<?> collect(ProductCollection collection) {
        productCollectionMapper.insert(collection);
        productCollectionMapper.addProductCollection(collection.getProductId());
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<?> cancelCollect(Long productId, Long userId) {
        productCollectionMapper.delete(productId, userId);
        productCollectionMapper.reduceProductCollection(productId);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<List<ProductCollectionVO>> myCollection(Long userId) {
        List<ProductCollectionVO> list = productCollectionMapper.selectByUserId(userId);
        return CommonResult.success(list);
    }
    /**
     * 判断是否已经收藏
     */
    @Override
    public boolean isCollected(Long userId, Long productId) {
        // 调用 mapper 查询 count > 0 表示已收藏
        Integer count = productCollectionMapper.selectCountByUserAndProduct(userId, productId);
        return count != null && count > 0;
    }

    @Override
    public CommonResult<Integer> getCollectionNumber(Long productId) {
        LambdaQueryWrapper<ProductCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductCollection::getProductId, productId);
        Integer CollectionCount = Math.toIntExact(productCollectionMapper.selectCount(queryWrapper));
        return CommonResult.success(CollectionCount);
    }
}