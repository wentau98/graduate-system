package com.secondhand.service.impl;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.OrderComment;
import com.secondhand.mapper.OrderCommentMapper;
import com.secondhand.service.CommentService;
import com.secondhand.vo.OrderCommentBuyerInfoVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private OrderCommentMapper orderCommentMapper;

    @Override
    public CommonResult<?> addComment(OrderComment comment) {
        orderCommentMapper.insert(comment);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<List<OrderComment>> getByProductId(Long productId) {
        List<OrderComment> list = orderCommentMapper.selectByProductId(productId);
        return CommonResult.success(list);
    }

    @Override
    public CommonResult<OrderCommentBuyerInfoVO> getAllMyComment(Long id) {
        return orderCommentMapper.getCommentsBySellerId(id);
    }
}