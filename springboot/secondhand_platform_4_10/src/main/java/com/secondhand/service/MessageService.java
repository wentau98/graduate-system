package com.secondhand.service;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.ChatMessage;
import com.secondhand.entity.SystemMessage;
import java.util.List;

public interface MessageService {
//    CommonResult<?> sendMessage(SystemMessage message);
//    CommonResult<List<SystemMessage>> getByUserId(Long userId);

    CommonResult<List<ChatMessage>> getMessagesWithSb(Long userId, Long otherId);

    CommonResult<List<ChatMessage>> getMessagesByContactId(Long contactId);
}