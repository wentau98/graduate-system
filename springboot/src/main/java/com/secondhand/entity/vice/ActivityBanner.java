package com.secondhand.entity.vice;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityBanner {
    private Long bannerId;
    private String bannerTitle;
    private String bannerUrl;
    private String bannerLink;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime updateTime; // 必须包含的更新时间字段
    private Integer isActive;
}