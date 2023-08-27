package com.sideproject.makeboard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BoardUpdateInfo {
    private Long id;
    private String password;
    private String title;
    private String content;
}
