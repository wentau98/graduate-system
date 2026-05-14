package com.secondhand.service;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.ChatMessage;
import java.util.List;

public interface ChatService {
    CommonResult<?> sendMsg(ChatMessage message);
    CommonResult<List<ChatMessage>> getHistory(Long senderId, Long receiverId);
}