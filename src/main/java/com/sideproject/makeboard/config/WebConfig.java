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
                .allowedOriginPatterns("http://*.roco.moe:*",
                        "https://*.roco.moe:*",
                        "http://*.roco.moe",
                        "https://*.roco.moe",
                        "http://mezvopydur.us16.qoddiapp.com:*",
                        "https://mezvopydur.us16.qoddiapp.com:*",
                        "http://mezvopydur.us16.qoddiapp.com",
                        "https://mezvopydur.us16.qoddiapp.com",
                        "http://localhost:*",
                        "https://localhost:*",
                        "http://localhost",
                        "https://localhost")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}

