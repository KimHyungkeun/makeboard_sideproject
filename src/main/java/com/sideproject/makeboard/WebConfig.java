package com.sideproject.makeboard;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://*.roco.moe:*", "https://*.roco.moe:*","http://localhost:*","https://localhost:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}

