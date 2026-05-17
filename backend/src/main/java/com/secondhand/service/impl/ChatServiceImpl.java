package com.secondhand.service.impl;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.ChatMessage;
import com.secondhand.handler.UserWebSocketHandler;
import com.secondhand.mapper.ChatMessageMapper;
import com.secondhand.mapper.UserInfoMapper;
import com.secondhand.service.ChatService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatMessageMapper chatMessageMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public CommonResult<?> sendMsg(ChatMessage message) {
        chatMessageMapper.insert(message);
        return CommonResult.success(null);
    }

    @Override
    public CommonResult<List<ChatMessage>> getHistory(Long senderId, Long receiverId) {
        List<ChatMessage> list = chatMessageMapper.selectHistory(senderId, receiverId);
        return CommonResult.success(list);
    }

    @Override
    public Object remindAudit(long productId,long userId) {
        String content = "用户提醒您尽快审核 "+"productId 为: "+productId+" 的商品!";
        String toUserId = String.valueOf(userInfoMapper.getUserIdByUsername("admin"));
        UserWebSocketHandler.sendToSomeOne(toUserId,content);
        return CommonResult.success(null);
    }
}