package com.secondhand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondhand.entity.ChatContact;
import com.secondhand.entity.ChatMessage;
import com.secondhand.mapper.ChatContactMapper;
import com.secondhand.mapper.SystemMessageMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.text.SimpleDateFormat;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

@Slf4j
//@Component
//@ServerEndpoint("/ws/user/{userId}")
public class UserWebSocketServer {
    // 让这个类能获取 Spring Bean
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
    // key: 用户ID  value: 连接会话
    private static final Map<String, Session> USER_SESSION_MAP = new ConcurrentHashMap<>();

    // 建立连接：前端带上自己userId
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        System.out.println("session id: " + session.getId());
        System.out.println("extractUserIdFromSession id: " + extractUserIdFromSession(session));
        USER_SESSION_MAP.put(userId, session);
        log.info("用户{} 已上线，建立消息长连接", extractUserIdFromSession(session));
    }

    // 断开连接
    @OnClose
    public void onClose(Session session,@PathParam("userId") String userId) {
        USER_SESSION_MAP.remove(userId);
        log.info("用户{} 下线", userId);
    }

    // ========== 关键：给指定卖家单发消息 ==========
    public static void sendToSeller(String sellerUserId, String content) {
        System.out.println("所有上线用户有：");
        for (Map.Entry<String, Session> entry : USER_SESSION_MAP.entrySet()) {
            System.out.println("key: "+entry.getKey()+" value: "+entry.getValue());
        }
        if (!USER_SESSION_MAP.containsKey(sellerUserId)) {
            log.info("卖家{} 当前不在线，无法实时推送", sellerUserId);
            return;
        }
        Session session = USER_SESSION_MAP.get(sellerUserId);
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(content);
            }
        } catch (Exception e) {
            log.error("推送卖家消息失败", e);
        }
    }
    // ===================== 核心工具方法 =====================
    // 从 ws://localhost:8081/ws/user/2 里取出 2
    private String extractUserIdFromSession(Session session) {
        String path = session.getRequestURI().getPath();
        return path.substring(path.lastIndexOf("/") + 1);
    }
    @OnMessage
    public void onMessage(String jsonString, Session session,@PathParam("userId") String userId) throws IOException {
        try {
            ObjectMapper om = new ObjectMapper();
            Map<String,Object> map = om.readValue(jsonString, Map.class);
            Long contactId = Long.parseLong(map.get("contactId").toString());
            Long toUserId = Long.parseLong(map.get("toUserId").toString());
            String content = (String) map.get("content");
            // 生成 HH:mm 格式时间：19:30
            String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            System.out.println("用户"+userId+"发送了"+content+"给用户:"+toUserId);
//            SystemMessageMapper messageMapper = applicationContext.getBean(SystemMessageMapper.class);
            // 生成雪花 ID，和 MP 默认规则完全一致
            Long id = IdWorker.getId();
            if (USER_SESSION_MAP.containsKey(toUserId.toString())) {
                // 1. 组装你要发的消息
                Map<String, Object> msg = new HashMap<>();
                msg.put("id",id);
                msg.put("contactId",contactId);
                msg.put("fromUserId", Long.parseLong(userId));
                msg.put("toUserId", toUserId);
                msg.put("content", content);
                msg.put("time", nowTime);
                // 2. 转成 JSON 字符串
                String json = om.writeValueAsString(msg);
                Session session1 = USER_SESSION_MAP.get(toUserId.toString());
                session1.getBasicRemote().sendText(json);
            }
            storeMessage(id,jsonString);
            updateLastMsg(jsonString);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // ======================= 修复 2：必须加错误捕获，否则公网直接断连 =======================
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("WebSocket异常", throwable);
    }
    private boolean storeMessage(Long id,String jsonString){
        try {
            ObjectMapper om = new ObjectMapper();
            Map<String,Object> map = om.readValue(jsonString, Map.class);
            Long toUserId = Long.parseLong(map.get("toUserId").toString());
            String content = (String) map.get("content");
            Long contactId = Long.parseLong(map.get("contactId").toString());
            Long fromUserId = Long.parseLong(map.get("fromUserId").toString());
            String time = map.get("time").toString();
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setId(id);
            chatMessage.setContactId(contactId);
            chatMessage.setFromUserId(fromUserId);
            chatMessage.setTime(time);
            chatMessage.setContent(content);
            chatMessage.setToUserId(toUserId);
            SystemMessageMapper messageMapper = applicationContext.getBean(SystemMessageMapper.class);
            messageMapper.insert(chatMessage);
            return chatMessage.getId()!=null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private boolean updateLastMsg(String jsonString){
        try {
            ObjectMapper om = new ObjectMapper();
            Map<String,Object> map = om.readValue(jsonString, Map.class);
            String content = (String) map.get("content");
            Long contactId = Long.parseLong(map.get("contactId").toString());
            ChatContactMapper chatContactMapper = applicationContext.getBean(ChatContactMapper.class);
            ChatContact chatContact = new ChatContact();
            chatContact.setId(contactId);
            chatContact.setLastMsg(content);
            chatContactMapper.updateById(chatContact);
            return chatContact.getId()!=null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}