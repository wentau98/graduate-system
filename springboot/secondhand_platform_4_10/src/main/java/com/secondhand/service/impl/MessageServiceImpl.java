package com.secondhand.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.ChatMessage;
import com.secondhand.entity.SystemMessage;
import com.secondhand.mapper.SystemMessageMapper;
import com.secondhand.service.MessageService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.Arrays;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private SystemMessageMapper systemMessageMapper;

//    @Override
//    public CommonResult<?> sendMessage(SystemMessage message) {
//        systemMessageMapper.insert(message);
//        return CommonResult.success(null);
//    }
//
//    @Override
//    public CommonResult<List<SystemMessage>> getByUserId(Long userId) {
//        List<SystemMessage> list = systemMessageMapper.selectByUserId(userId);
//        return CommonResult.success(list);
//    }

    @Override
    public CommonResult<List<ChatMessage>> getMessagesWithSb(Long userId, Long otherId) {
        // 你要查询的用户ID数组（比如 A=101, B=102）
        List<Long> userIds = Arrays.asList(userId,otherId);
        // Lambda 方式查询
        List<ChatMessage> messageList = systemMessageMapper.selectList(
                Wrappers.lambdaQuery(ChatMessage.class)
                        .in(ChatMessage::getFromUserId, userIds)  // from_user_id in (?)
                        .in(ChatMessage::getToUserId, userIds)    // to_user_id in (?)
                        .orderByAsc(ChatMessage::getCreateTime)   // 按时间正序
        );
        return CommonResult.success(messageList);
    }

    @Override
    public CommonResult<List<ChatMessage>> getMessagesByContactId(Long contactId) {
        List<ChatMessage> messageList = systemMessageMapper.selectList(
                Wrappers.lambdaQuery(ChatMessage.class)
                        .eq(ChatMessage::getContactId, contactId)
                        .orderByAsc(ChatMessage::getCreateTime)   // 按时间正序
        );
        return CommonResult.success(messageList);
    }
}