package com.secondhand.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserReport {
    private Long reportId;
    private Long reportUserId;
    private Long reportedUserId;
    private Long productId;
    private Integer reportType;
    private String reportReason;
    private String reportContent;
    private Integer reportStatus;
    private String handleResult;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}