package com.secondhand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("region")
public class Region {

    // 地区ID 主键自增
    @TableId(type = IdType.AUTO)
    private Integer regionId;

    // 地区名称（省/市/区/街道）
    private String regionName;

    // 父地区ID（0=顶级省份）
    private Integer parentId;

    // 层级 1=省 2=市 3=区县 4=街道乡镇
    private Integer level;

    // 展示排序
    private Integer sort;

    // 行政区划编码
    private String areaCode;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}