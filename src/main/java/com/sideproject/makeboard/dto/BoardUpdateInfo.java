package com.sideproject.makeboard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // 생성자를 사용하지 않도록 선언
public class BoardUpdateInfo {
    private Long id;
    private String password;
    private String title;
    private String content;
}
