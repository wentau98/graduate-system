package com.secondhand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "管理员用户实体")
public class AdminUser {

    @Schema(description = "管理员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "ad001")
    @TableId(type = IdType.AUTO) // 👈 关键！数据库自增
    private Long adminId;

    @Schema(description = "登录账号", example = "admin")
    private String username;

    @Schema(description = "登录密码", example = "123456")
    private String password;

    @Schema(description = "真实姓名", example = "系统管理员")
    private String realName;

    @Schema(description = "联系电话", example = "13800138000")
    private String phone;

    @Schema(description = "角色 0-超级管理员 1-普通管理员", example = "0")
    private Integer role;

    @Schema(description = "状态 0-禁用 1-正常", example = "1")
    private Integer adminStatus;

    @Schema(description = "创建时间", example = "2025-01-01T12:00:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", example = "2025-01-01T12:00:00")
    private LocalDateTime updateTime;
}