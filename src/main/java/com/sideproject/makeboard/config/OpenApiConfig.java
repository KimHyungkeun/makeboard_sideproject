package com.sideproject.makeboard.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("게시판 API 목록")
                .version(springdocVersion)
                .description("게시판 조회, 등록, 수정, 생성의 API를 모은 Swagger");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
