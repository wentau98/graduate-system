package com.secondhand.entity.vice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 新闻实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("news")
public class News {

    /**
     * 主键id 自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻内容
     */
    private String content;

    /**
     * 创建用户
     */
    private Long createUserId;

    /**
* 创建时间                 
     */
    private LocalDateTime createTime;

    private int status;
    private String createUserName;

}