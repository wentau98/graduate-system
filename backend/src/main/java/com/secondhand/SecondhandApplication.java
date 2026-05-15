package com.secondhand;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.secondhand.mapper")  // ✅ 扫描所有 Mapper
public class SecondhandApplication {
    public static void main(String[] args) {
//        SpringApplication.run(SecondhandApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(SecondhandApplication.class, args);

        // 关键：传入上下文
        UserWebSocketServer.setApplicationContext(context);
    }
}