package com.secondhand.config;

import com.secondhand.handler.UserWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket // ⚠️ 必须开启 Spring WebSocket 支持
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private UserWebSocketHandler userWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(userWebSocketHandler, "/ws/user/{userId}") // 映射你的原路径
                .setAllowedOrigins("*"); // ⚠️ 彻底拍死跨域保护，Jar包运行不再拦截
    }
}