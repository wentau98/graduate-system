package com.secondhand.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfo {
    private Long userId;
    private String username;
    private String password;
    private String realName;
    private String studentId;
    private String phone;
    private String avatar;
    private Integer creditScore;
    private Integer userStatus;
    private String disableReason;
    private LocalDateTime disableTime;
    private LocalDateTime enableTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    //新增所在地
    private  String location;
}