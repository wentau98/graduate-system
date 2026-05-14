package com.secondhand.service.impl;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.RefundRecord;
import com.secondhand.mapper.RefundRecordMapper;
import com.secondhand.service.RefundService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x

@Service
public class RefundServiceImpl implements RefundService {

    @Resource
    private RefundRecordMapper refundRecordMapper;

    @Override
    public CommonResult<?> applyRefund(RefundRecord refund) {
        refundRecordMapper.insert(refund);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<?> auditRefund(Long refundId, Integer status) {
        refundRecordMapper.updateStatus(refundId, status);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<RefundRecord> getRefundByOrderId(Long orderId) {
        RefundRecord refund = refundRecordMapper.selectByOrderId(orderId);
        return CommonResult.success(refund);
    }
}