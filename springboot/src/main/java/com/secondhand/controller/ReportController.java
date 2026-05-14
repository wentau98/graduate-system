package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.UserReport;
import com.secondhand.service.ReportService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Resource
    private ReportService reportService;

    @PostMapping("/submit")
    public CommonResult<?> submit(@RequestBody UserReport report) {
        return reportService.submitReport(report);
    }

    @GetMapping("/admin/list")
    public CommonResult<List<UserReport>> list() {
        return reportService.getAllReports();
    }
}