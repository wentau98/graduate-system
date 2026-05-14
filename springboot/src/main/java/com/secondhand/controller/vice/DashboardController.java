package com.secondhand.controller.vice;

import com.secondhand.common.CommonResult;
import com.secondhand.mapper.vice.DashboardMapper;
import com.secondhand.vo.DashboardVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    @Resource
    private DashboardMapper dashboardMapper;

    @GetMapping("/overview")
    public CommonResult<DashboardVO> overview() {
        DashboardVO vo = new DashboardVO();

        // 数字统计
        Map<String, Object> num = dashboardMapper.getTotalNumbers();
        vo.setUserTotal(((Number) num.get("userTotal")).intValue());
        vo.setProductPublished(((Number) num.get("productPublished")).intValue());
        vo.setOrderFinished(((Number) num.get("orderFinished")).intValue());
        vo.setUserViolation(((Number) num.get("userViolation")).intValue());
        vo.setProductViolation(((Number) num.get("productViolation")).intValue());
        vo.setOrderDispute(((Number) num.get("orderDispute")).intValue());

        // 折线图
        vo.setUserTrend(dashboardMapper.getUserTrend());
        vo.setProductTrend(dashboardMapper.getProductTrend());
        vo.setOrderTrend(dashboardMapper.getOrderTrend());

        // 饼图
        vo.setProductStatusPie(dashboardMapper.getProductStatusPie());
        vo.setOrderStatusPie(dashboardMapper.getOrderStatusPie());

        return CommonResult.success(vo);
    }
}