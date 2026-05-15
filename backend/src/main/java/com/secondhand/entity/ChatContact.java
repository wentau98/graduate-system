package com.secondhand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@TableName("chat_contact")
public class ChatContact {
    @TableId(type = IdType.AUTO)
    private Long id;          // 会话ID
    private Long userOne;  // 当前用户ID
    private Long userTwo;    // 对方用户ID
//    private String name;      // 对方昵称
    private String lastMsg;   // 最后一条消息
    private Integer status;   // 1正常 0删除
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}