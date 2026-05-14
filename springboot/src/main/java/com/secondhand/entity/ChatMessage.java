package com.secondhand.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("chat_message")
public class ChatMessage {
    @TableId
    private Long id;          // 消息ID
    private Long contactId;   // 会话ID
    private Long fromUserId;  // 发送人ID
    private Long toUserId;    // 接收人ID
    private String content;   // 消息内容
    private String time;      // 显示时间
    private LocalDateTime createTime;
//    private Long msgId;
//    private Long senderId;
//    private Long receiverId;
//    private Long productId;
//    private String content;
//    private Integer msgStatus;
//    private LocalDateTime sendTime;
}