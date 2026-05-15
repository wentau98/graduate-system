package com.secondhand.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SystemMessage {
    private Long msgId;
    private Long userId;
    private String msgTitle;
    private String msgContent;
    private Integer msgType;
    private Integer isRead;
    private LocalDateTime createTime;
}