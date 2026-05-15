package com.secondhand.vo;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class DashboardVO {
    // 核心数字统计
    private Integer userTotal;
    private Integer productPublished;
    private Integer orderFinished;
    private Integer userViolation;
    private Integer productViolation;
    private Integer orderDispute;

    // 折线图：日期 -> 数量
    private List<Map<String, Object>> userTrend;
    private List<Map<String, Object>> productTrend;
    private List<Map<String, Object>> orderTrend;

    // 饼图：名称 -> 数量
    private List<Map<String, Object>> productStatusPie;
    private List<Map<String, Object>> orderStatusPie;
}