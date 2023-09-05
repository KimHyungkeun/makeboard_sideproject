package com.sideproject.makeboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // allowdOrigin : 허용 Protocal,IP,Port 설정
    // allowedMehotd : 허용 REST method 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        "https?://*.roco.moe:*",
                        "https?://*.roco.moe",
                        "https?://localhost:*",
                        "https?://localhost")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}

