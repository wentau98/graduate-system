package com.secondhand.service;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.OrderComment;
import com.secondhand.vo.OrderCommentBuyerInfoVO;

import java.util.List;

public interface CommentService {
    CommonResult<?> addComment(OrderComment comment);
    CommonResult<List<OrderComment>> getByProductId(Long productId);

    CommonResult<OrderCommentBuyerInfoVO> getAllMyComment(Long id);
}