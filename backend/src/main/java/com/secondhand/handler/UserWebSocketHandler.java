package com.secondhand.handler;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondhand.entity.ChatContact;
import com.secondhand.entity.ChatMessage;
import com.secondhand.mapper.ChatContactMapper;
import com.secondhand.mapper.SystemMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class UserWebSocketHandler extends TextWebSocketHandler {

    // ⚠️ 新写法最大的福音：可以直接正常注入Mapper了，不再需要 applicationContext.getBean 
    @Autowired
    private SystemMessageMapper messageMapper;

    @Autowired
    private ChatContactMapper chatContactMapper;

    private final ObjectMapper om = new ObjectMapper();

    // 用来存放所有在线用户的 Session
    private static final Map<String, WebSocketSession> USER_SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 对应原先的 @OnOpen
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = extractUserIdFromSession(session);
        USER_SESSION_MAP.put(userId, session);
        log.info("用户{} 已上线，建立消息长连接", userId);
    }

    /**
     * 对应原先的 @OnClose
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = extractUserIdFromSession(session);
        if (userId != null) {
            USER_SESSION_MAP.remove(userId);
            log.info("用户{} 下线", userId);
        }
    }

    /**
     * 对应原先的 @OnMessage
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String jsonString = message.getPayload(); // 拿到前端发的 JSON 字符串
        String userId = extractUserIdFromSession(session);

        try {
            Map<String, Object> map = om.readValue(jsonString, Map.class);
            Long contactId = Long.parseLong(map.get("contactId").toString());
            Long toUserId = Long.parseLong(map.get("toUserId").toString());
            String content = (String) map.get("content");

            String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            System.out.println("用户" + userId + "发送了" + content + "给用户:" + toUserId);

            Long id = IdWorker.getId();

            // 如果对方在线，实时推送
            if (USER_SESSION_MAP.containsKey(toUserId.toString())) {
                Map<String, Object> msg = new HashMap<>();
                msg.put("id", id);
                msg.put("contactId", contactId);
                msg.put("fromUserId", Long.parseLong(userId));
                msg.put("toUserId", toUserId);
                msg.put("content", content);
                msg.put("time", nowTime);

                String json = om.writeValueAsString(msg);
                WebSocketSession targetSession = USER_SESSION_MAP.get(toUserId.toString());
                if (targetSession != null && targetSession.isOpen()) {
                    targetSession.sendMessage(new TextMessage(json));
                }
            }

            // 存盘与更新最后消息
            storeMessage(id, map);
            updateLastMsg(contactId, content);

        } catch (Exception e) {
            log.error("处理消息发生异常", e);
        }
    }

    /**
     * 对应原先的 @OnError
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket异常, sessionId: {}", session.getId(), exception);
        if (session.isOpen()) {
            session.close();
        }
    }

    /**
     * 对应原先的静态群发方法 sendToSeller
     */
    public static void sendToSeller(String sellerUserId, String content) {
        System.out.println("所有上线用户有：");
        for (Map.Entry<String, WebSocketSession> entry : USER_SESSION_MAP.entrySet()) {
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue().getId());
        }
        if (!USER_SESSION_MAP.containsKey(sellerUserId)) {
            log.info("卖家{} 当前不在线，无法实时推送", sellerUserId);
            return;
        }
        WebSocketSession session = USER_SESSION_MAP.get(sellerUserId);
        try {
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(content));
            }
        } catch (Exception e) {
            log.error("推送卖家消息失败", e);
        }
    }
    /**
     * send to specified person
     */
    public static void sendToSomeOne(String userId, String content) {
        if (!USER_SESSION_MAP.containsKey(userId)) {
            log.info("{}当前不在线，无法实时推送", userId);
            return;
        }
        WebSocketSession session = USER_SESSION_MAP.get(userId);
        try {
            if (session != null && session.isOpen()) {
                log.info("{}been pushed", userId);
                session.sendMessage(new TextMessage(content));
            }
        } catch (Exception e) {
            log.error("推送消息失败", e);
        }
    }

    /**
     * 工具方法：从 URI 中提取最后的 userId
     */
    private String extractUserIdFromSession(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.substring(path.lastIndexOf("/") + 1);
    }

    /**
     * 存储消息（去掉了繁琐的二次反序列化，直接用已有的 map）
     */
    private void storeMessage(Long id, Map<String, Object> map) {
        try {
            Long toUserId = Long.parseLong(map.get("toUserId").toString());
            String content = (String) map.get("content");
            Long contactId = Long.parseLong(map.get("contactId").toString());
            Long fromUserId = Long.parseLong(map.get("fromUserId").toString());
            String time = map.get("time") != null ? map.get("time").toString() : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setId(id);
            chatMessage.setContactId(contactId);
            chatMessage.setFromUserId(fromUserId);
            chatMessage.setTime(time);
            chatMessage.setContent(content);
            chatMessage.setToUserId(toUserId);

            messageMapper.insert(chatMessage);
        } catch (Exception e) {
            log.error("存储消息失败", e);
        }
    }

    /**
     * 更新最后一条消息
     */
    private void updateLastMsg(Long contactId, String content) {
        try {
            ChatContact chatContact = new ChatContact();
            chatContact.setId(contactId);
            chatContact.setLastMsg(content);
            chatContactMapper.updateById(chatContact);
        } catch (Exception e) {
            log.error("更新最后一条消息失败", e);
        }
    }
}