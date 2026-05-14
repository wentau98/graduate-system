package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.ChatMessage;
import com.secondhand.entity.SystemMessage;
import com.secondhand.service.MessageService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Resource
    private MessageService messageService;

//    @PostMapping("/send")
//    public CommonResult<?> send(@RequestBody SystemMessage message) {
//        return messageService.sendMessage(message);
//    }
//
//    @GetMapping("/user/{userId}")
//    public CommonResult<List<SystemMessage>> list(@PathVariable Long userId) {
//        return messageService.getByUserId(userId);
//    }
    @GetMapping("/get-list")
    public CommonResult<List<ChatMessage>> getWithSb(@RequestParam String userId, @RequestParam String otherId) {
        long user = Long.parseLong(userId);
        long other = Long.parseLong(otherId);
        return messageService.getMessagesWithSb(user,other);
    }
    @GetMapping("/get-messages/{id}")
    public CommonResult<List<ChatMessage>> getMessages(@PathVariable String id) {
        Long contactId = Long.parseLong(id);
        return messageService.getMessagesByContactId(contactId);
    }
}