package com.secondhand.service;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.UserReport;
import java.util.List;

public interface ReportService {
    CommonResult<?> submitReport(UserReport report);
    CommonResult<List<UserReport>> getAllReports();
}