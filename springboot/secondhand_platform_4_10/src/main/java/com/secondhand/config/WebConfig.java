package com.secondhand.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /// 商品图片
        registry.addResourceHandler("/upload/product/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/upload/product/");

        // 用户头像
        registry.addResourceHandler("/upload/user/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/upload/user/");
        // 品牌图片
        registry.addResourceHandler("/upload/brand/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/upload/brand/");
    }
}