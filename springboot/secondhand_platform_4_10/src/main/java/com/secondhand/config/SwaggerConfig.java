package com.secondhand.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI secondhandOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Secondhand Platform API")
                        .description("Campus Secondhand Trading Platform Backend APIs")
                        .version("1.0.0"));
    }
}