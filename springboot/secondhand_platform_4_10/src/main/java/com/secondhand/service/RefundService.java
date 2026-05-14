package com.secondhand.service;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.RefundRecord;

public interface RefundService {
    CommonResult<?> applyRefund(RefundRecord refund);
    CommonResult<?> auditRefund(Long refundId, Integer status);
    CommonResult<RefundRecord> getRefundByOrderId(Long orderId);
}