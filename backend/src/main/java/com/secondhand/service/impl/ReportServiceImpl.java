package com.secondhand.service.impl;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.UserReport;
import com.secondhand.mapper.UserReportMapper;
import com.secondhand.service.ReportService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private UserReportMapper userReportMapper;

    @Override
    public CommonResult<?> submitReport(UserReport report) {
        userReportMapper.insert(report);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<List<UserReport>> getAllReports() {
        List<UserReport> list = userReportMapper.selectAll();
        return CommonResult.success(list);
    }
}