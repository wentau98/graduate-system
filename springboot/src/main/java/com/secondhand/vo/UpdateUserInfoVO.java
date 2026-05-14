package com.secondhand.vo;

import lombok.Data;

@Data
public class UpdateUserInfoVO {
    private int userId;
    private String avatar;
    private String username;
    private String password;
    private String location;
    private String phone;
}
