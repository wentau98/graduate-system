package com.secondhand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("product_category")  // 必须加
public class ProductCategory {
        @TableId(type = IdType.AUTO)
        private Integer categoryId;
    private String categoryName;
    private Integer parentId;
    private Integer level;
    private Integer sort;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    // 子分类（数据库不存在，仅用于组装树形结构）
    // 👇👇👇 就是这里！必须加！
    @TableField(exist = false)
    private List<ProductCategory> children;
}