package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.ChatMessage;
import com.secondhand.entity.Product;
import com.secondhand.service.ChatService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    @PostMapping("/send")
    public CommonResult<?> send(@RequestBody ChatMessage message) {
        return chatService.sendMsg(message);
    }

    @GetMapping("/history")
    public CommonResult<List<ChatMessage>> history(@RequestParam Long senderId, @RequestParam Long receiverId) {
        return chatService.getHistory(senderId, receiverId);
    }
    @GetMapping("/remind/audit/{productId}/{userId}")
    public CommonResult<?> remindAudit(@PathVariable("productId") long productId,@PathVariable("userId") long userId) {
        return CommonResult.success(chatService.remindAudit(productId,userId));
    }
}